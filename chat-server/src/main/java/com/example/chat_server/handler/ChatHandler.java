// src/main/java/com/example/chatserver/handler/ChatHandler.java
package com.example.chat_server.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;

import java.util.ArrayList;
import java.util.List;

public class ChatHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session); // Add the new session
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Broadcast the received message to all connected sessions
        for (WebSocketSession s : sessions) {
            try {
                s.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session); // Remove the session on disconnect
    }
}
