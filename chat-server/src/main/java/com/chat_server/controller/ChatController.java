// src/main/java/com/chat_server/controller/ChatController.java
package com.chat_server.controller;

import com.chat_server.model.Message;
import com.chat_server.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ChatController - Gère les messages entrants via STOMP (/app/chat).
 * Assure le stockage (synchrone) et la diffusion (asynchrone).
 */
@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
        logger.info("[CTRL-STOMP] ChatController initialisé avec ChatService.");
    }

    /**
     * Reçoit le message d'un client (/app/chat), le stocke et le diffuse.
     * @param message - Le message reçu du client (Frontend).
     * @return Message - Le même message, diffusé à tous les abonnés.
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        logger.info("[CTRL-STOMP] Message reçu de {}: \"{}\"", message.getSender(), message.getText());

        try {
            // 1. Stockage du message (Synchrone)
            chatService.addMessage(message);
            logger.debug("[CTRL-STOMP] Message stocké dans ChatService. Prêt à diffuser.");
        } catch (Exception e) {
            logger.error("[CTRL-STOMP] ERREUR lors du stockage du message : {}", e.getMessage());
            // Même en cas d'erreur de stockage, on diffuse pour ne pas bloquer le chat temps réel.
        }

        // 2. Diffusion à tous les clients abonnés au topic (Asynchrone)
        return message; 
    }
}