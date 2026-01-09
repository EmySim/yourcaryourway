// src/main/java/com/chat_server/model/Message.java
package com.chat_server.model;

/**
 * Message - Modèle de données pour les messages échangés.
 */
public class Message {
    private String text;
    private String sender;
    // Note: Pas de timestamp pour simplifier le POC

    public Message() {}

    public Message(String text, String sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    @Override
    public String toString() {
        return "[Message] { text: \"" + text + "\", sender: \"" + sender + "\" }";
    }
}