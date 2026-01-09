import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Client, Message, IFrame } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { HttpClient } from '@angular/common/http'; // Nécessaire pour les appels HTTP si on ajoute l'historique ou autres API

/**
 * ChatComponent - Composant principal du chat utilisant STOMP/SockJS
 * Communication asynchrone en temps réel.
 */
@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class ChatComponent implements OnInit, OnDestroy {
  // Message en cours de saisie
  message: string = '';
  // Liste des messages affichés
  messages: { text: string; sender: string }[] = [];
  // Rôle de l'utilisateur: 'client' ou 'helpdesk'
  public userRole: string = '';
  // ID de la session (utilisé comme émetteur)
  public sessionId: string = '';
  // Indique si un envoi est en cours pour désactiver l'UI
  public isLoading: boolean = false;
  // Afficher la sélection du rôle
  public showRoleSelection: boolean = true;
  // Statut de la connexion
  public connectionStatus: string = 'Sélectionnez votre rôle...';

  private stompClient: Client;

  // URL du point de terminaison WebSocket/SockJS Spring Boot
  private readonly CHAT_ENDPOINT = 'http://localhost:8080/chat';

  constructor(private http: HttpClient) {
    console.log('[CHAT] Initialisation du composant.');
    
    // Initialisation du client STOMP avec SockJS
    this.stompClient = new Client({
      webSocketFactory: () => new SockJS(this.CHAT_ENDPOINT)
    });
  }

  /**
   * Sélectionne le rôle et démarre la connexion.
   */
  selectRole(role: string) {
    console.log(`[CHAT] Rôle sélectionné: ${role}`);
    this.userRole = role;
    this.sessionId = role; // Utilisation du rôle comme ID simple pour POC
    this.showRoleSelection = false;
    this.connectionStatus = 'Connexion STOMP en cours...';
    this.connectToWebSocket();
  }

  ngOnInit() {
    console.log('[CHAT] ngOnInit - prêt à connecter après sélection de rôle.');
  }

  ngOnDestroy() {
    console.log('[CHAT] ngOnDestroy - Déconnexion STOMP.');
    if (this.stompClient && this.stompClient.connected) {
      this.stompClient.deactivate();
      console.log('[CHAT] Client STOMP désactivé.');
    }
  }

  /**
   * Établit la connexion WebSocket/STOMP et s'abonne.
   */
  connectToWebSocket() {
    this.stompClient.onConnect = () => {
      console.log('[STOMP] Connexion réussie au WebSocket.');
      this.connectionStatus = 'Connecté ✓';

      // S'ABONNER au topic de diffusion /topic/messages
      this.stompClient.subscribe('/topic/messages', (message: Message) => {
        console.log(`[STOMP] Message reçu du broker.`);
        try {
          const { text, sender } = JSON.parse(message.body);
          this.receiveMessage(text, sender);
        } catch (e) {
          console.error('[STOMP] Erreur lors du parsing du message:', e);
        }
      });
    };

    this.stompClient.onStompError = (frame: IFrame) => {
      console.error('[STOMP] Erreur STOMP / Broker:', frame);
      this.connectionStatus = 'Erreur de connexion ✗';
    };

    this.stompClient.onWebSocketClose = (event: CloseEvent) => {
      console.warn('[STOMP] WebSocket fermé:', event);
      this.connectionStatus = 'Déconnecté ';
    };

    // Active la connexion
    this.stompClient.activate();
  }

  /**
   * Envoie le message au serveur via la destination /app/chat.
   */
  sendMessage() {
    if (!this.stompClient.connected) {
      console.warn('[CHAT] Non connecté, envoi annulé.');
      this.connectionStatus = 'Non connecté, tentez de rafraîchir.';
      return;
    }

    if (this.message.trim()) {
      this.isLoading = true;
      const messageToSend = { text: this.message, sender: this.sessionId };
      
      console.log(`[CHAT] Publication sur /app/chat: "${this.message}"`);

      try {
        // PUBLICATION (Asynchrone)
        this.stompClient.publish({
          destination: '/app/chat',
          body: JSON.stringify(messageToSend)
        });
        
        // Affichage immédiat pour l'expérience utilisateur
        this.messages.push(messageToSend); 
        this.message = ''; // Vider l'input

        console.log('[CHAT] Message affiché localement et envoyé.');
      } finally {
        this.isLoading = false;
      }
    } else {
      console.log('[CHAT] Message vide, envoi annulé.');
    }
  }

  /**
   * Gère la réception d'un message diffusé par le serveur.
   */
  receiveMessage(text: string, sender: string) {
    // Éviter d'afficher en double le message envoyé par cette session
    if (sender !== this.sessionId) { 
      console.log(`[CHAT] Reçu d'un tiers (${sender}): "${text}"`);
      this.messages.push({ text, sender });
    } else {
      console.log(`[CHAT] Message de la propre session reçu, ignoré pour éviter le doublon.`);
    }
  }

  /**
   * Obtient le nom d'affichage du rôle
   */
  getRoleDisplayName(role: string): string {
    return role === 'client' ? 'Client' : 'Helpdesk';
  }
}