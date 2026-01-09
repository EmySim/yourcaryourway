# 💬 Chat Application - Angular + Spring Boot

<div align="center">

Une application de **chat en temps réel** utilisant **WebSockets/STOMP** pour la communication instantanée entre un client Angular et un serveur Spring Boot.

[![Angular](https://img.shields.io/badge/Angular-18-red)](https://angular.io)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-green)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-MIT-blue)](#)

</div>

---

## 📋 Prérequis

| Technologie | Version minimale | Vérification |
|---|---|---|
| **Node.js** | ≥ 18.x | `node --version` |
| **Java JDK** | 17+ | `java -version` |
| **Maven** | ≥ 3.8.x | `mvn --version` |
| **npm** | ≥ 9.x | `npm --version` |

---

## 📁 Structure du Projet

```
Chat-Application-Angular-Spring-Boot-main/
│
├── 📁 chat-app-angular/                    # Frontend Angular 18
│   ├── src/app/
│   │   ├── chat/
│   │   │   ├── chat.component.ts           # Logique STOMP/WebSocket
│   │   │   ├── chat.component.html         # Template
│   │   │   └── chat.component.css          # Styles
│   │   ├── services/
│   │   │   └── websocket.service.ts        # Service HTTP/REST
│   │   └── app.config.ts
│   ├── package.json                        # Dépendances Angular
│   └── angular.json
│
├── 📁 chat-server/                         # Backend Spring Boot 3.3
│   ├── src/main/java/com/chat_server/
│   │   ├── ChatServerApplication.java      # Point d'entrée
│   │   ├── WebSocketConfig.java            # Configuration STOMP
│   │   ├── controller/
│   │   │   ├── ChatController.java         # Endpoints WebSocket
│   │   │   └── MessageRestController.java  # Endpoints REST
│   │   ├── service/
│   │   │   └── ChatService.java            # Stockage en mémoire
│   │   ├── model/
│   │   │   └── Message.java                # Modèle Message
│   │   └── exception/
│   │       └── GlobalExceptionHandler.java # Gestion erreurs
│   ├── pom.xml                             # Dépendances Maven
│   └── mvnw
│
├── README.md (ce fichier)
├── QUICK_START.md                          # Guide de démarrage rapide
└── ARCHITECTURE_DIAGRAMS.md                # Diagrammes d'architecture
```

---

## 🚀 Démarrage Rapide

### 1️⃣ Démarrer le Backend (Spring Boot)

```bash
cd chat-server
mvn spring-boot:run
```

**Vérification :**
- ✅ Logs : `[CONFIG] Configuration de l'endpoint STOMP '/chat' avec SockJS`
- ✅ Serveur accessible sur `ws://localhost:8080/chat`
- ✅ REST API disponible sur `http://localhost:8080/api/messages`

### 2️⃣ Démarrer le Frontend (Angular)

```bash
cd chat-app-angular
npm install
npx ng serve
```

Ou si Angular CLI est installé globalement :
```bash
ng serve
```

**Vérification :**
- ✅ Application accessible sur `http://localhost:4200`
- ✅ Console logs : WebSocket connection successful

---

## 📡 Stack Technologique

### Frontend (Angular)
- **Framework:** Angular 18
- **Langage:** TypeScript 5.5
- **WebSocket:** @stomp/stompjs, SockJS
- **HTTP:** HttpClient (Angular)

### Backend (Spring Boot)
- **Framework:** Spring Boot 3.3
- **WebSocket:** Spring WebSocket + STOMP
- **Serveur:** Embedded Tomcat
- **Langage:** Java 17

---

## 🔄 Flux de Communication

L'application utilise une **architecture asynchrone** basée sur STOMP/WebSocket pour une communication bidirectionnelle en temps réel.

### Diagramme du Flux

```
┌─ Angular Client (localhost:4200)          Spring Boot (localhost:8080) ─┐
│                                                                          │
│  (1) Connexion WebSocket/SockJS                                         │
│      └─────────────────────────────────────────────────> [WebSocket]    │
│                                                                          │
│  (2) Abonnement au topic /topic/messages                                │
│      └─────────────────────────────────────────────────> [Broker]       │
│                                                                          │
│  (3) Envoi d'un message via /app/chat                                   │
│      └─────────────────────────────────────────────────> [Controller]   │
│                                                          [Service]       │
│  (4) Message diffusé à tous les clients abonnés                         │
│      <─────────────────────────────────────────────────── [Broker]      │
│                                                                          │
│  ✓ Réception instantanée du message                                     │
│      [Affichage dans le chat]                                           │
│                                                                          │
└──────────────────────────────────────────────────────────────────────────┘
```

### Endpoints

| Type | Destination | Utilisation | Fichier |
|------|---|---|---|
| **PUBLISH** | `/app/chat` | Client envoie un message | ChatController.java |
| **SUBSCRIBE** | `/topic/messages` | Clients reçoivent les messages | ChatController.java |
| **REST GET** | `/api/messages` | Récupère l'historique | MessageRestController.java |
| **REST POST** | `/api/messages` | Ajoute un message (fallback) | MessageRestController.java |

---

## 💻 Utilisation de l'Application

### Étapes

1. **Ouvrir deux navigateurs** (ou deux onglets) sur `http://localhost:4200`

2. **Sélectionner un rôle :**
   - Client
   - Helpdesk

3. **Envoyer un message :**
   - Tapez votre message
   - Appuyez sur Entrée ou cliquez sur "Envoyer"

4. **Observez la communication en temps réel :**
   - Le message s'affiche instantanément dans votre session
   - Les autres clients abonnés reçoivent le message immédiatement

### Caractéristiques

✅ **Communication en temps réel** via WebSocket  
✅ **Support de plusieurs rôles** (Client/Helpdesk)  
✅ **Stockage en mémoire** des messages  
✅ **CORS configuré** pour Angular  
✅ **Gestion des erreurs** globale  
✅ **API REST** pour fallback HTTP  

---

## 🐛 Dépannage

| Problème | Cause | Solution |
|---|---|---|
| **WebSocket connection failed** | Backend non démarré | Vérifier : `mvn spring-boot:run` sur le port 8080 |
| **CORS Error** | Origine non autorisée | Vérifier `WebSocketConfig.java` : `.setAllowedOrigins("http://localhost:4200")` |
| **Cannot find module '@stomp/stompjs'** | Dépendances npm non installées | Exécuter `npm install` dans `chat-app-angular/` |
| **Module 'sockjs-client' not found** | Types TypeScript manquants | Vérifier `@types/sockjs-client` dans `package.json` |
| **No static resource .well-known/appspecific/com.chrome.devtools.json** | Chrome DevTools requête 404 (inoffensif) | ⚠️ Avertissement cosmétique - aucune action requise |
| **Messages en double** | Logique de filtrage sessionId manquante | Vérifier `receiveMessage()` : `if (sender !== this.sessionId)` |
| **ng: command not found** | Angular CLI non installé localement | Utiliser `npx ng serve` ou installer globalement : `npm install -g @angular/cli` |

---

## 📚 Documentation Complète

Pour plus de détails, consultez :

- **[QUICK_START.md](./QUICK_START.md)** — Guide pas-à-pas complet
- **[ARCHITECTURE_DIAGRAMS.md](./ARCHITECTURE_DIAGRAMS.md)** — Diagrammes détaillés
- **[SYNCHRONOUS_ARCHITECTURE.md](./SYNCHRONOUS_ARCHITECTURE.md)** — Architecture synchrone expliquée

---

## 🔐 Architecture de Sécurité

### CORS
- ✅ Frontend autorisé : `http://localhost:4200`
- ✅ Backend : Spring WebSocket CORS configuré

### Validation
- ✅ Messages non vides
- ✅ Gestion des exceptions centralisée
- ✅ Logs structurés avec SLF4J

---

## 📈 Points de Scalabilité

Pour une utilisation en production :

1. **Remplacer le Simple Broker** par RabbitMQ ou ActiveMQ
2. **Ajouter une base de données** pour la persistance (PostgreSQL, MongoDB)
3. **Implémenter l'authentification** (JWT, OAuth2)
4. **Ajouter les tests unitaires** (JUnit, Jest)
5. **Configurer le load balancing** (Nginx, HAProxy)

---

## 📄 License

MIT License - Libre d'utilisation
