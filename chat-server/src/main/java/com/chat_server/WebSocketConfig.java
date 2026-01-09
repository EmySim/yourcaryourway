// src/main/java/com/chat_server/WebSocketConfig.java
package com.chat_server;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebSocketConfig - Active la gestion des messages STOMP/WebSocket.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        logger.info("[CONFIG] Configuration de l'endpoint STOMP '/chat' avec SockJS.");
        // Le point d'entrée pour la connexion
        registry.addEndpoint("/chat") 
                .setAllowedOrigins("http://localhost:4200") // CORS pour le frontend Angular
                .withSockJS(); // Fallback pour les navigateurs ne supportant pas WebSocket
    }

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        // Le Simple Broker diffuse les messages aux destinations commençant par /topic
        registry.enableSimpleBroker("/topic"); 
        logger.info("[CONFIG] Broker activé pour les destinations '/topic'.");
        
        // Préfixe pour les messages entrants du client vers les contrôleurs (@MessageMapping)
        registry.setApplicationDestinationPrefixes("/app");
        logger.info("[CONFIG] Destinations applicatives réglées sur '/app'.");
    }
}