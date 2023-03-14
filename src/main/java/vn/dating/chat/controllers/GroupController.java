package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.messages.api.CreateGroupDto;
import vn.dating.chat.dto.messages.api.ResultGroupDto;
import vn.dating.chat.mapper.GroupMapper;
import vn.dating.chat.mapper.UserMapper;
import vn.dating.chat.model.*;
import vn.dating.chat.services.GroupMemberService;
import vn.dating.chat.services.GroupService;
import vn.dating.chat.services.UserService;
import vn.dating.chat.utils.ApiGroupResponse;
import vn.dating.chat.utils.ApiGroupType;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
@Slf4j
public class GroupController {


    @Autowired
    private  GroupService groupService;

    @Autowired
    private GroupMemberService groupMemberService;

    @Autowired
    private  UserService userService;



    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createGroup(@RequestBody CreateGroupDto createGroupDto, Principal principal) {

        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if(currentUser ==null){
            return ResponseEntity.badRequest().build();
        }

        if(createGroupDto.getType() == GroupType.PRIVATE){
            if(createGroupDto.getMember().size()==1){
            String userEmail2 = createGroupDto.getMember().get(0);

                List<Long> listGroup = groupService.existChatTwoUser(principalName, userEmail2);
                if(listGroup==null){
                    List<String> members = createGroupDto.getMember();
                    if(members.contains(principalName)){
                        members.remove(principalName);
                    }
                    members.add(principalName);



                    List<User> userList = userService.findUsersByEmails(members);
                    if(members.size() == userList.size()){
                        Group group = new Group();
                        group.setAdmin(currentUser);
                        group.setCreatedAt(Instant.now());
                        group.setType(GroupType.PRIVATE);
                        group.setRandom(GroupRandomType.NONE);
                        group.generateUrl();;
                        group.setName("PRIVATE");

                        group = groupService.saveGroup(group);
                        if(group==null){
                            return  ResponseEntity.badRequest().build();
                        }else{
                            ResultGroupDto resultGroupDto = groupService.createGroup(group,userList, currentUser,GroupMemberType.PRIVATE);
                            return ResponseEntity.ok(resultGroupDto);
                        }

                    }else {
                        return  ResponseEntity.badRequest().build();
                    }
                }
                if(listGroup.size()==1){
                    long groupId = listGroup.get(0);
                    return ResponseEntity.ok(groupService.getChatInfoGroup(groupId,currentUser));
                }
                if(listGroup.size()>1){
                    return ResponseEntity.ok("System is error");
                }
            }

            return ResponseEntity.badRequest().build();
        }
        else if (createGroupDto.getType() == GroupType.PUBLIC) {

            List<String> members = createGroupDto.getMember();
            if(members.contains(principalName)){
                members.remove(principalName);
            }
            members.add(principalName);

            List<User> userList = userService.findUsersByEmails(members);
            if(members.size() == userList.size()){
                Group group = new Group();
                group.setAdmin(currentUser);
                group.setCreatedAt(Instant.now());
                group.setType(GroupType.PUBLIC);
                group.setRandom(GroupRandomType.NONE);
                group.generateUrl();;
                group.setName("PUBLIC");

                group = groupService.saveGroup(group);
                if(group==null){
                    return  ResponseEntity.badRequest().build();
                }else{
                    ResultGroupDto resultGroupDto = groupService.createGroup(group,userList,currentUser,GroupMemberType.JOINED);
                    return ResponseEntity.ok(resultGroupDto);
                }

            }else {
                return  ResponseEntity.badRequest().build();
            }

        }else if (createGroupDto.getType() == GroupType.RANDOM) {
            return ResponseEntity.ok(GroupType.RANDOM);
        }
        return ResponseEntity.ok("Not working");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group updatedGroup) {
        Group existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        existingGroup.setName(updatedGroup.getName());
        groupService.saveGroup(existingGroup);
        return ResponseEntity.ok(existingGroup);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getGroup(@PathVariable Long id,Principal principal) {

        Group existingGroup = groupService.getGroupById(id);
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (existingGroup == null || currentUser==null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(groupService.getChatInfoGroup(id,currentUser));
    }

    @GetMapping("/with/{email}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity chatWithUser(@PathVariable String  email,Principal principal){
        User withUser = userService.getUserByEmail(email);
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (withUser == null || currentUser==null) {
            return ResponseEntity.badRequest().build();
        }

        List<Long> listGroup = groupService.existChatTwoUser(principalName, withUser.getEmail());
        if(listGroup ==null){


            return ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EMPTY,UserMapper.toGetContact(withUser)));
        } else if (listGroup.size()==1) {
            long groupId = listGroup.get(0);
            return ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EXIST,groupService.getChatInfoGroup(groupId,currentUser)));
        }else {

        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getGroupsForUser(Principal principal) {
        User user = userService.findByEmail(principal.getName()).orElse(null);
        Map<Group, List<User>> data = groupService.getGroupsForUser(user.getId());


        List<ResultGroupDto> resultGroupDtos = new ArrayList<>();
        data.forEach((group,members)->{
            resultGroupDtos.add(GroupMapper.toGetGroupOfUser(group,user,members));
        });

        if(resultGroupDtos.size()==0){
            return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EMPTY,null));
        }
        return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EXIST,resultGroupDtos));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
//        Group existingGroup = groupService.getGroupById(id);
//        if (existingGroup == null) {
//            return ResponseEntity.notFound().build();
//        }
//        groupService.deleteGroupById(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping("/{groupId}/members")
//    public ResponseEntity<GroupMember> addGroupMember(@PathVariable Long groupId, @RequestBody GroupMember groupMember) {
//        Group existingGroup = groupService.getGroupById(groupId);
//        if (existingGroup == null) {
//            return ResponseEntity.notFound().build();
//        }
//        groupMember.setGroup(existingGroup);
//        groupMemberService.saveGroupMember(groupMember);
//        return ResponseEntity.ok(groupMember);
//    }
//
//    @DeleteMapping("/{groupId}/members/{id}")
//    public ResponseEntity<Void> removeGroupMember(@PathVariable Long groupId, @PathVariable Long id) {
//        GroupMember existingGroupMember = groupMemberService.getGroupMemberById(id);
//        if (existingGroupMember == null || !existingGroupMember.getGroup().getId().equals(groupId)) {
//            return ResponseEntity.notFound().build();
//        }
//        groupMemberService.deleteGroupMemberById(id);
//        return ResponseEntity.noContent().build();
//    }

}
