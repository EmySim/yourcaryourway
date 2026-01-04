// src/main/java/com/example/chatserver/controller/ChatController.java
package com.example.chat_server.controller;

import com.example.chat_server.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        return message; // Broadcast the same payload to all clients
    }
}
