// src/app/services/websocket.service.ts
import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  private socket$: WebSocketSubject<string>;

  constructor() {
    this.socket$ = webSocket('ws://localhost:8080/chat');
  }

  sendMessage(msg: string) {
    this.socket$.next(msg);
  }

  getMessages() {
    return this.socket$.asObservable();
  }
}
