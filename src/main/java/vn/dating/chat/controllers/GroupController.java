package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.messages.api.CreatePublicGroupDto;
import vn.dating.chat.dto.messages.api.CreatePrivateGroupDto;
import vn.dating.chat.dto.messages.api.ResultGroupDto;
import vn.dating.chat.mapper.GroupMapper;
import vn.dating.chat.mapper.UserMapper;
import vn.dating.chat.model.*;
import vn.dating.chat.services.GroupMemberService;
import vn.dating.chat.services.GroupService;
import vn.dating.chat.services.UserService;
import vn.dating.chat.utils.ApiGroupResponse;
import vn.dating.chat.utils.ApiGroupType;
import vn.dating.chat.utils.AppConstants;
import vn.dating.chat.utils.PagedResponse;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    @PostMapping("/private")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createPrivateGroup(@RequestBody CreatePrivateGroupDto createPrivateGroupDto, Principal principal) {

        String currentUserName = principal.getName();
        User currentUser = userService.findByEmail(currentUserName).orElse(null);

        String withUser = createPrivateGroupDto.getWithEmail();

        List<String> members = new ArrayList<>();
        members.add(currentUserName);
        members.add(withUser);

        List<User> userList = userService.findUsersByEmails(members);
        if(userList.size()==2){
            List<Long> listGroup = groupService.existChatTwoUser(currentUserName, withUser);

            if(listGroup ==null){
                Group group = new Group();
                group.setAdmin(currentUser);
                group.setCreatedAt(Instant.now());
                group.setType(GroupType.PRIVATE);
                group.setRandom(GroupRandomType.NONE);
                group.generateUrl();
                group.setName("PRIVATE");

                group = groupService.saveGroup(group);

                if (group == null) {
                    return ResponseEntity.badRequest().build();
                } else {
                    ResultGroupDto resultGroupDto = groupService.createGroup(group, userList, currentUser, GroupMemberType.PRIVATE);
                    return ResponseEntity.ok(resultGroupDto);
                }
            }
            else if(listGroup.size()==1){
                long groupId = listGroup.get(0);

                // return create group
                return ResponseEntity.ok(groupService.getChatInfoGroup(groupId,currentUser));
            }else{
                return ResponseEntity.badRequest().body("System is error");
            }

        }else{
            return ResponseEntity.badRequest().build();
        }
    };



    @PostMapping("/public")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createPublicGroup(@RequestBody CreatePublicGroupDto createPublicGroupDto, Principal principal) {

        String currentUserName = principal.getName();
        User currentUser = userService.findByEmail(currentUserName).orElse(null);
        log.info(currentUserName);

        List<String> members = createPublicGroupDto.getMember();
        members.add(currentUserName);

        List<String> listWithoutDuplicates = new ArrayList<>();
        for (String element : members) {
            if (!listWithoutDuplicates.contains(element)) {
                listWithoutDuplicates.add(element);
            }
        }

        List<User> userList = userService.findUsersByEmails(listWithoutDuplicates);

//        log.info(String.valueOf(userList.size()));
//        log.info(String.valueOf(listWithoutDuplicates.size()));

        if(listWithoutDuplicates.size() == userList.size() && userList.size() != 2){
            Group group = new Group();
            group.setAdmin(currentUser);
            group.setCreatedAt(Instant.now());
            group.setType(GroupType.PUBLIC);
            group.setRandom(GroupRandomType.NONE);
            group.generateUrl();;
            group.setName(createPublicGroupDto.getName());

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
    }

//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group updatedGroup) {
//        Group existingGroup = groupService.getGroupById(id);
//        if (existingGroup == null) {
//            return ResponseEntity.notFound().build();
//        }
//        existingGroup.setName(updatedGroup.getName());
//        groupService.saveGroup(existingGroup);
//        return ResponseEntity.ok(existingGroup);
//    }

    @GetMapping("/{groupId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getGroup(@PathVariable Long groupId,Principal principal) {

        Group existingGroup = groupService.getGroupById(groupId);
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (existingGroup == null || currentUser==null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(groupService.getChatInfoGroup(groupId,currentUser));
    }

    @GetMapping("/private/with/{email}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity chatPrivateWithUser(@PathVariable String  email,Principal principal){
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
        }
        return ResponseEntity.badRequest().build();
    }

//    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity getGroupsForUser(Principal principal) {
//
//
//        User user = userService.findByEmail(principal.getName()).orElse(null);
//        Map<Group, List<User>> data = groupService.getGroupsForUser(user.getId());
//
//
//        List<ResultGroupDto> resultGroupDtos = new ArrayList<>();
//        data.forEach((group,members)->{
//            resultGroupDtos.add(GroupMapper.toGetGroupOfUser(group,user,members));
//        });
//
//        if(resultGroupDtos.size()==0){
//            return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EMPTY,null));
//        }
//        return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EXIST,resultGroupDtos));
//    }

    @GetMapping("/mee")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getGroupsForUsers(Principal principal ,
                                           @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        User currentUser = userService.findByEmail(principal.getName()).orElse(null);
        PagedResponse pagedResponse = groupService.findGroupOfUser(currentUser,page,size);

        return  ResponseEntity.ok(pagedResponse);
//        if(resultGroupDtos.size()==0){
//            return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EMPTY,null));
//        }
//        return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EXIST,resultGroupDtos));
    }

    @GetMapping("/groupTop")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getTopNewMessageOfGroups(Principal principal ,
                                                   @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        User currentUser = userService.findByEmail(principal.getName()).orElse(null);
        PagedResponse pagedResponse = groupService.findTopGroupOfUser(currentUser,page,size);

        return  ResponseEntity.ok(pagedResponse);
//        if(resultGroupDtos.size()==0){
//            return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EMPTY,null));
//        }
//        return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EXIST,resultGroupDtos));
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
