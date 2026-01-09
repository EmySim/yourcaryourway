// src/main/java/com/chat_server/service/ChatService.java
package com.chat_server.service;

import org.springframework.stereotype.Service;
import com.chat_server.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ChatService - Gère la logique d'application et le stockage des messages.
 * (Stockage en mémoire pour le POC - PAS d'historique au démarrage)
 */
@Service
public class ChatService {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    
    // Liste synchronisée pour le stockage en mémoire (pas thread-safe sans Collections.synchronizedList, 
    // mais suffisant pour un POC simple)
    private final List<Message> messages = Collections.synchronizedList(new ArrayList<>());
    
    public ChatService() {
        logger.info("[SERVICE] ChatService initialisé. Messages en mémoire : 0.");
    }

    /**
     * Ajoute un nouveau message à la liste. (Opération Synchrone/Bloquante)
     * @param message - Le message à ajouter.
     * @return Message - Le message ajouté.
     */
    public Message addMessage(Message message) {
        if (message == null || message.getText() == null || message.getText().trim().isEmpty()) {
            logger.warn("[SERVICE] Tentative d'ajouter un message vide. Opération annulée.");
            throw new IllegalArgumentException("Le message ne peut pas être vide.");
        }
        
        messages.add(message);
        logger.debug("[SERVICE] Message ajouté. Total actuel: {}", messages.size());
        return message;
    }
    
    /**
     * Récupère tous les messages actuellement stockés.
     * @return List<Message> - Liste des messages.
     */
    public List<Message> getAllMessages() {
        logger.info("[SERVICE] Récupération de l'historique : {} messages.", messages.size());
        // Retourne une copie pour éviter les modifications externes
        return new ArrayList<>(messages); 
    }
}