package vn.dating.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.dating.chat.dto.MessageDto;
import vn.dating.chat.service.WSService;

@RestController
public class WsController {

    @Autowired
    private WSService wsService;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody MessageDto messageDto){
        wsService.notifyFrontEnd(messageDto);

    }

    @PostMapping("/send-private-message/{id}")
    public void sendPrivateMessage(@PathVariable String id,
                                    @RequestBody MessageDto messageDto){
        wsService.notifyUser(id,messageDto.getContent());

    }
}
