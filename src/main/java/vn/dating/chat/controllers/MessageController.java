package vn.dating.chat.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.messages.api.ResultGroupMessage;
import vn.dating.chat.model.UserReceive;
import vn.dating.chat.securities.CurrentUser;
import vn.dating.chat.services.UserReceiveService;
import vn.dating.chat.services.UserService;
import vn.dating.chat.utils.AppConstants;
import vn.dating.chat.utils.PagedResponse;

import java.nio.file.attribute.UserPrincipal;


@RestController
@RequestMapping("/api/v1/messages")
@Slf4j
public class MessageController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserReceiveService userReceiveService;

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

//        @GetMapping
//    public ResultGroupMessage getAllMovies(@CurrentUser UserPrincipal currentUser,
//                                           @RequestParam(value = "groupId") long groupId,
//                                           @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
//                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
//
//            ResultGroupMessage resultGroupMessage = userReceiveService.findMessageByGroupId(groupId,page,size);
//            return  resultGroupMessage;
//    }

}
