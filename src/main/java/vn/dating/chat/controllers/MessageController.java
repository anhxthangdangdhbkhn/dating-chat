package vn.dating.chat.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.dating.chat.dto.messages.ChatInfo;
import vn.dating.chat.dto.messages.CreateGroupDto;
import vn.dating.chat.mapper.UserMapper;
import vn.dating.chat.model.User;
import vn.dating.chat.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@Slf4j
public class MessageController {
    @Autowired
    private UserService userService;

    private List<ChatInfo> roomDtoList = new ArrayList<>();

    @PostMapping("/rooms")
    public ResponseEntity createRoom(@RequestBody CreateGroupDto createGroupDto, Principal principal) {
        if(createGroupDto.getMember().size()==1){

            String current = principal.getName();
            String user2 = createGroupDto.getMember().get(0);
            ChatInfo checkIsExist = getChat(current,user2);

            if(checkIsExist==null){
                log.info("Create new a singer chat by {}",principal.getName());

                User user = userService.getUserByEmail(current);

                List<String> listUser = new ArrayList<>();
                listUser.add(current);
                listUser.add(user2);


                ChatInfo newChatSinger = new ChatInfo(listUser,user.getUsername(), createGroupDto.getTime());

                List<User> userList = userService.findUsersByEmails(listUser);

                log.info(UserMapper.toGetListUsers(userList).toString());
                newChatSinger.setMemberInfo(UserMapper.toGetListUsers(userList));
                roomDtoList.add(newChatSinger);

                return ResponseEntity.ok(newChatSinger);
            }else {
                log.info("Load chat {} and {}",current,user2);
                return ResponseEntity.ok(checkIsExist);
            }

        }else{
            log.info("Create new a room {} by {}", createGroupDto.getName(),principal.getName());
            List<String> members = new ArrayList<>();
            members.add(principal.getName());

            createGroupDto.getMember().forEach(m->{
                members.add(m);
            });

            List<User> getUserDataBase = userService.findUsersByEmails(members);

            ChatInfo chatInfo = new ChatInfo(members, principal.getName(), createGroupDto.getName(), createGroupDto.getTime());
            chatInfo.setMemberInfo(UserMapper.toGetListUsers(getUserDataBase));

            roomDtoList.add(chatInfo);

            return ResponseEntity.ok(chatInfo);
        }
    }

    @GetMapping("/rooms")
    public List<ChatInfo> getAllRooms() {
        return roomDtoList;
    }

    public ChatInfo getChat(String user1, String user2){
        for(int i =0; i < roomDtoList.size();i++){
            ChatInfo chatInfo = roomDtoList.get(i);
            List<String> member = roomDtoList.get(i).getMember();
            log.info(member.toString());
            if(member.size()==2){
                if(member.get(0).contains(user1) && member.get(1).contains(user2)){
                    return chatInfo;
                }
                if(member.get(0).contains(user2) && member.get(1).contains(user1)){
                    return chatInfo;
                }
            }
        }
        return null;
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity getRoom(@PathVariable int id) {

        ChatInfo chatInfo = roomDtoList.stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);

        if (chatInfo == null) {
            return ResponseEntity.notFound().build();
        }

        List<String> members = chatInfo.getMember();
        List<User> getUserDataBase = userService.findUsersByEmails(members);
        chatInfo.setMemberInfo(UserMapper.toGetListUsers(getUserDataBase));
        return ResponseEntity.ok(chatInfo);
    }
}
