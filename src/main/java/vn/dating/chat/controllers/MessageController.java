package vn.dating.chat.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.messages.api.ResultGroupMessage;
import vn.dating.chat.model.UserReceive;
import vn.dating.chat.securities.CurrentUser;
import vn.dating.chat.services.GroupService;
import vn.dating.chat.services.MessageService;
import vn.dating.chat.services.UserReceiveService;
import vn.dating.chat.services.UserService;
import vn.dating.chat.utils.AppConstants;
import vn.dating.chat.utils.PagedResponse;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;


@RestController
@RequestMapping("/api/v1/messages")
@Slf4j
public class  MessageController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserReceiveService userReceiveService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private GroupService groupService;

//    @GetMapping
//    public PagedResponse getAllMovies(@CurrentUser UserPrincipal currentUser,
//                                      @RequestParam(value = "groupId") long groupId,
//                                      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//                                      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
//
//        Page<UserReceive> userReceivePage = userReceiveService.findByGroupChatId(groupId,page,size);
//        //return movieService.getAllMovies(currentUser, page, size);
//        return null;
//    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getMessagesOfGroup(Principal principal,
                                             @RequestParam(value = "groupId") long groupId,
                                             @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {

        log.info("principal.getName() {}",principal.getName());
            boolean isMemberOfGroup = groupService.isUserMemberOfGroup(principal.getName() ,groupId);
            if(!isMemberOfGroup) return  ResponseEntity.badRequest().build();


        PagedResponse resultGroupMessage = messageService.findMessageByGroupId(groupId,page,size);
            return  ResponseEntity.ok(resultGroupMessage);
    }

}
