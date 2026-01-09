// src/main/java/com/example/chat_server/ChatServerApplication.java
package com.chat_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ChatServerApplication - Point d'entrée de l'application Spring Boot
 * Serveur de chat utilisant une architecture synchrone REST
 */
@SpringBootApplication
public class ChatServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(ChatServerApplication.class);

    public static void main(String[] args) {
        logger.info("Démarrage du serveur de chat...");
        SpringApplication.run(ChatServerApplication.class, args);
        logger.info("Serveur de chat démarré avec succès!");
        logger.info("API disponible sur: http://localhost:8080/api");
        logger.info("- GET  /api/messages - Récupérer les messages");
        logger.info("- POST /api/messages - Envoyer un message");
        logger.info("- DELETE /api/messages - Effacer les messages");
        logger.info("- GET /api/health - Vérifier l'état du serveur");
    }
}
