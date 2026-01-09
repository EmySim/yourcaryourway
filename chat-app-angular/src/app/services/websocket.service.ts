// src/app/services/websocket.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

/**
 * WebSocketService - Service de communication avec le serveur
 * Gère les appels HTTP pour envoyer et recevoir les messages
 * Utilise une approche synchrone avec Promises au lieu de RxJS Observables
 */
@Injectable({
  providedIn: 'root'
})
export class WebSocketService {
  // URL de base du serveur
  private apiUrl = 'http://localhost:8080/api';

  /**
   * Constructeur - Injecte le service HttpClient
   */
  constructor(private http: HttpClient) {
    console.log('[WebSocketService] Service initialisé');
    console.log('[WebSocketService] URL API:', this.apiUrl);
  }

  /**
   * Envoie un message au serveur
   * Effectue une requête POST sur /api/messages avec le texte et l'auteur
   * @param msg - Le texte du message à envoyer
   * @param sender - L'ID de session de l'émetteur
   * @returns Promise<any> - Promesse contenant la réponse du serveur
   */
  sendMessage(msg: string, sender: string): any {
    const messagePayload = { text: msg, sender: sender };
    console.log('[WebSocketService] Envoi du message:', messagePayload);
    console.log('[WebSocketService] URL cible: POST', `${this.apiUrl}/messages`);
    
    const promise = this.http.post(`${this.apiUrl}/messages`, messagePayload).toPromise();
    
    promise.then((response) => {
      console.log('[WebSocketService] Réponse du serveur pour l\'envoi:', response);
    }).catch((error) => {
      console.error('[WebSocketService] ERREUR lors de l\'envoi du message:', error);
      console.error('[WebSocketService] Détails de l\'erreur:', error.message);
    });
    
    return promise;
  }

  /**
   * Récupère tous les messages du serveur
   * Effectue une requête GET sur /api/messages
   * @returns Promise<any> - Promesse contenant la liste des messages
   */
  getMessages(): any {
    console.log('[WebSocketService] Récupération des messages...');
    console.log('[WebSocketService] URL cible: GET', `${this.apiUrl}/messages`);
    
    const promise = this.http.get(`${this.apiUrl}/messages`).toPromise();
    
    promise.then((response) => {
      console.log('[WebSocketService] Réponse reçue du serveur:', response);
      if (Array.isArray(response)) {
        console.log(`[WebSocketService] ${response.length} message(s) reçu(s)`);
      }
    }).catch((error) => {
      console.error('[WebSocketService] ERREUR lors de la récupération des messages:', error);
      console.error('[WebSocketService] Détails de l\'erreur:', error.message);
      console.error('[WebSocketService] Status HTTP:', error.status);
      console.error('[WebSocketService] URL qui a échoué:', `${this.apiUrl}/messages`);
    });
    
    return promise;
  }
}
