package vn.dating.chat.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.messages.api.CreatePublicGroupDto;
import vn.dating.chat.dto.messages.api.CreatePrivateGroupDto;
import vn.dating.chat.dto.messages.api.ResultGroupMembersOfGroupDto;
import vn.dating.chat.model.*;
import vn.dating.chat.services.GroupMemberService;
import vn.dating.chat.services.GroupService;
import vn.dating.chat.services.MessageService;
import vn.dating.chat.services.UserService;
import vn.dating.chat.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private MessageService messageService;


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
                    ResultGroupMembersOfGroupDto resultGroupMembersOfGroupDto = groupService.createGroup(group, userList, currentUser, GroupMemberType.PRIVATE);
                    return ResponseEntity.ok(resultGroupMembersOfGroupDto);
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
                ResultGroupMembersOfGroupDto resultGroupMembersOfGroupDto = groupService.createGroup(group,userList,currentUser,GroupMemberType.JOINED);
                return ResponseEntity.ok(resultGroupMembersOfGroupDto);
            }

        }else {
            return  ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{groupId}/member")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMembersOfGroup(@PathVariable Long groupId,Principal principal, HttpServletRequest request) {
        Group existingGroup = groupService.getGroupById(groupId);
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (existingGroup == null || currentUser==null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(groupService.getChatInfoGroup(groupId,currentUser));
    }

    @GetMapping("/{groupId}/connect")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getConnectToGroup(@PathVariable Long groupId,Principal principal) {

        Group existingGroup = groupService.getGroupById(groupId);
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (existingGroup == null || currentUser==null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(groupService.getGroupMembersAndLastMessagesOfGroup(existingGroup,principalName));
    }

    @GetMapping("/private/with/{email}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity chatPrivateWithUser(@PathVariable String  email,Principal principal){
        User withUser = userService.getUserByEmail(email);
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (currentUser==null) {
            return ResponseEntity.notFound().build();
        }
        if(withUser == null  )   return ResponseEntity.notFound().build();


        List<Long> listGroup = groupService.existChatTwoUser(principalName, withUser.getEmail());
        if(listGroup ==null){
            return ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EMPTY,"Group is not exist"));
        } else if (listGroup.size()==1) {
            long groupId = listGroup.get(0);
            Group group = groupService.getGroupById(groupId);
            return ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EXIST,groupService.getGroupMembersAndLastMessagesOfGroup(group,currentUser.getEmail())));
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/mee")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getGroupsForUsers(Principal principal ,
                                           @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        User currentUser = userService.findByEmail(principal.getName()).orElse(null);
        PagedResponse pagedResponse = groupService.findGroupOfUser(currentUser,page,size);


        if(pagedResponse.getSize()==0){
            return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EMPTY,null));
        }
        return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EXIST,pagedResponse));

    }

    @GetMapping("/top")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getTopNewMessageOfGroups(Principal principal ,
                                                   @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        User currentUser = userService.findByEmail(principal.getName()).orElse(null);
        PagedResponse pagedResponse = groupService.findTopGroupsAndTopMessagesOfUser(currentUser,page,size);

        if(pagedResponse.getSize()==0){
            return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EMPTY,null));
        }
        return  ResponseEntity.ok(new ApiGroupResponse(ApiGroupType.EXIST,pagedResponse));
    }

    @GetMapping("/next/{groupId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getNextMessageOfGroup(@PathVariable Long groupId,Principal principal) {

        Group existingGroup = groupService.getGroupById(groupId);
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (existingGroup == null || currentUser==null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(groupService.getChatInfoGroup(groupId,currentUser));
    }
    @GetMapping("/before/{groupId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getBeforeMessageOfGroup(@PathVariable Long groupId,Principal principal,
                                                  @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                                  @RequestParam(value = "time") Long createdAtAfter) {

//        Instant afterTime = TimeHelper.strToInstant(createdAtAfter);

        Instant afterTime = TimeHelper.milliToInstant(createdAtAfter);

        Group existingGroup = groupService.getGroupById(groupId);
        String principalName = principal.getName();
        User currentUser = userService.findByEmail(principalName).orElse(null);

        if (existingGroup == null || currentUser==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messageService.findMessagesByGroupIdAfterOrderByCreatedAtDesc(groupId,page,size, afterTime));
    }
}
