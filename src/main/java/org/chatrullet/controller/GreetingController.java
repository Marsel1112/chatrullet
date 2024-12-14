package org.chatrullet.controller;

import lombok.RequiredArgsConstructor;
import org.chatrullet.model.UserDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.chatrullet.service.ChatService;

@Controller
@RequiredArgsConstructor
public class GreetingController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/connect")
    public void greeting(@RequestBody UserDTO userDTO){
        try {
            UserDTO matchedUser = chatService.getUser(userDTO);
            messagingTemplate.convertAndSend("/queue/user-" + userDTO.getName(), matchedUser);
            messagingTemplate.convertAndSend("/queue/user-" + matchedUser.getName(), userDTO);
        }catch (RuntimeException | InterruptedException runtimeException){
            return;
        }
    }
}
