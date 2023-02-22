package vn.dating.chat.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.messages.CreateGroupDto;
import vn.dating.chat.dto.messages.ResultGroupDto;
import vn.dating.chat.mapper.GroupMapper;
import vn.dating.chat.mapper.GroupMemberMapper;
import vn.dating.chat.mapper.UserMapper;
import vn.dating.chat.model.Group;
import vn.dating.chat.model.GroupMember;
import vn.dating.chat.model.User;
import vn.dating.chat.services.GroupMemberService;
import vn.dating.chat.services.GroupService;
import vn.dating.chat.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {

    private final GroupService groupService;
    private final GroupMemberService groupMemberService;
    private final UserService userService;

    public GroupController(GroupService groupService, GroupMemberService groupMemberService,UserService userService) {
        this.groupService = groupService;
        this.groupMemberService = groupMemberService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity createGroup(@RequestBody CreateGroupDto createGroupDto, Principal principal) {


        String adminEmail = principal.getName();
        User admin = userService.findByEmail(adminEmail).orElse(null);



        if(admin ==null){
            return ResponseEntity.badRequest().build();
        }

        Group group = new Group();
        group.setName(createGroupDto.getName());
        group.setAdmin(admin);
        group.setName(createGroupDto.getName());

        List<String> members = createGroupDto.getMember();
        if(members.contains(adminEmail)){
            members.remove(adminEmail);
        }
        if(members.size()<2){
            return ResponseEntity.badRequest().build();
        }
        members.add(adminEmail);

        List<User> userList = userService.findUsersByEmails(members);

        group = groupService.saveGroup(group);

        List<GroupMember>   groupMembers = groupService.addUsersToGroup(group,userList);

        ResultGroupDto resultGroupDto = GroupMapper.toGetGroup(group);

        List<User> groupUser = new ArrayList<>();
        groupMembers.forEach(m->{
            groupUser.add(m.getUser());
        });

        resultGroupDto.setMembers(UserMapper.toGetListUsers(groupUser));
        return ResponseEntity.ok(resultGroupDto);
    }

    @PutMapping("/{id}")
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
    public ResponseEntity getGroup(@PathVariable Long id) {
        Group existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }

        ResultGroupDto resultGroupDto = GroupMapper.toGetGroup(existingGroup);

        List<GroupMember> groupMembers = groupMemberService.getGroupMembersByGroupId(id);

        List<User> listUser = new ArrayList<>();
        groupMembers.forEach(m->{
            listUser.add(m.getUser());
        });
        resultGroupDto.setMembers(UserMapper.toGetListUsers(listUser));
        return ResponseEntity.ok(resultGroupDto);
    }

    @GetMapping("/forUer/{uerId}")
    public ResponseEntity getGroupsForUser(@PathVariable Long uerId) {
        Map<Group, List<User>> data = groupService.getGroupsForUser(uerId);

        List<ResultGroupDto> resultGroupDtos = new ArrayList<>();
        data.forEach((group,members)->{
            resultGroupDtos.add(GroupMapper.toGetGroupOfUser(group,members));
        });
        return  ResponseEntity.ok(resultGroupDtos);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        Group existingGroup = groupService.getGroupById(id);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        groupService.deleteGroupById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<GroupMember> addGroupMember(@PathVariable Long groupId, @RequestBody GroupMember groupMember) {
        Group existingGroup = groupService.getGroupById(groupId);
        if (existingGroup == null) {
            return ResponseEntity.notFound().build();
        }
        groupMember.setGroup(existingGroup);
        groupMemberService.saveGroupMember(groupMember);
        return ResponseEntity.ok(groupMember);
    }

    @DeleteMapping("/{groupId}/members/{id}")
    public ResponseEntity<Void> removeGroupMember(@PathVariable Long groupId, @PathVariable Long id) {
        GroupMember existingGroupMember = groupMemberService.getGroupMemberById(id);
        if (existingGroupMember == null || !existingGroupMember.getGroup().getId().equals(groupId)) {
            return ResponseEntity.notFound().build();
        }
        groupMemberService.deleteGroupMemberById(id);
        return ResponseEntity.noContent().build();
    }

}
