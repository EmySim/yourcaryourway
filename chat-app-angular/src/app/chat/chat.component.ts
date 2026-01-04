// src/app/chat/chat.component.ts
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class ChatComponent implements OnInit {
  message: string = '';
  messages: { text: string; sender: string }[] = [];
  private stompClient: Client;
  public sessionId: string; // Changed to public

  constructor() {
    this.sessionId = this.generateSessionId(); // Unique ID for this session
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/chat')
    });
  }

  ngOnInit() {
    this.connectToWebSocket();
  }

  generateSessionId(): string {
    return Math.random().toString(36).substring(2, 15); // Generate a unique session ID
  }

  connectToWebSocket() {
    this.stompClient.onConnect = () => {
      console.log('Connected to WebSocket');

      // Subscribe to the chat topic
      this.stompClient.subscribe('/topic/messages', (message: Message) => {
        const { text, sender } = JSON.parse(message.body);
        this.receiveMessage(text, sender);
      });
    };

    this.stompClient.activate();
  }

  sendMessage() {
    if (this.message.trim()) {
      // Send the message to the server with the current session's ID as sender
      this.stompClient.publish({
        destination: '/app/chat',
        body: JSON.stringify({ text: this.message, sender: this.sessionId })
      });
      // Display the message on the sender's side
      this.messages.push({ text: this.message, sender: this.sessionId });
      this.message = ''; // Clear the input field
    }
  }

  receiveMessage(text: string, sender: string) {
    // Display the received message only if it's from a different sender
    if (sender !== this.sessionId) {
      this.messages.push({ text, sender });
    }
  }
}
