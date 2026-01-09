# 💬 POC Chat Application - Angular + Spring Boot

<div align="center">

![Angular](https://img.shields.io/badge/Angular-18.2-red?style=flat-square&logo=angular)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-green?style=flat-square&logo=spring)
![TypeScript](https://img.shields.io/badge/TypeScript-5.5-blue?style=flat-square&logo=typescript)
![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![WebSocket](https://img.shields.io/badge/WebSocket-SockJS-yellow?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

Une application de chat en temps réel moderne et performante. Échangez des messages instantanément via une connexion WebSocket sécurisée entre le frontend Angular et le backend Spring Boot.

[🚀 Démarrage rapide](#démarrage-rapide) • [📋 Prérequis](#prérequis) • [🏗️ Architecture](#architecture) • [💻 Utilisation](#utilisation)

</div>

---

## 🌟 Caractéristiques

✅ **Messagerie en temps réel** - Communication instantanée via WebSocket  
✅ **Interface utilisateur intuitive** - Chat moderne et réactif avec Angular 18  
✅ **Architecture scalable** - Spring Boot pour une backend robuste et performante  
✅ **Sessions uniques** - Identification automatique des utilisateurs  
✅ **Affichage contextuel** - Messages alignés à droite pour l'expéditeur, à gauche pour les autres  
✅ **Support multi-sessions** - Plusieurs utilisateurs peuvent discuter simultanément  

---

## 📋 Prérequis

Avant de démarrer, assurez-vous d'avoir les éléments suivants installés :

| Technologie | Version |
|-------------|---------|
| **Node.js** | ≥ 18.x |
| **npm** | ≥ 9.x |
| **Angular CLI** | ≥ 18.x |
| **Java JDK** | 17+ |
| **Maven** | ≥ 3.8.x |

```bash
# Vérifier les versions
node --version
npm --version
ng version
java -version
mvn --version
```

---

## 🏗️ Architecture

```
Chat-Application/
├── 📁 chat-app-angular/          # Frontend Angular 18
│   ├── src/
│   │   ├── app/
│   │   │   ├── chat/             # Composant principal du chat
│   │   │   ├── services/         # Service WebSocket
│   │   │   └── app.component.ts  # Composant racine
│   │   ├── index.html
│   │   └── styles.css
│   ├── angular.json
│   └── package.json
│
└── 📁 chat-server/               # Backend Spring Boot 3.3
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/example/chat_server/
    │   │   │       ├── ChatServerApplication.java
    │   │   │       ├── WebSocketConfig.java
    │   │   │       ├── controller/
    │   │   │       ├── handler/
    │   │   │       └── model/
    │   │   └── resources/
    │   │       └── application.properties
    │   └── test/
    ├── pom.xml
    └── mvnw
```

### Stack Technologique

**Frontend:**
- Angular 18.2
- TypeScript 5.5
- RxJS 7.8
- SockJS + STOMP
- Angular Forms Module

**Backend:**
- Spring Boot 3.3.5
- Spring WebSocket
- Spring Web
- Java 17
- Maven

---

## 🚀 Démarrage rapide

### 1️⃣ Cloner le repository

```bash
git clone <votre-url-repo>
cd Chat-Application-Angular-Spring-Boot-main
```

### 2️⃣ Démarrer le Backend (Spring Boot)

```bash
cd chat-server

# Construire et lancer l'application
mvn clean install
mvn spring-boot:run

# Le serveur démarre sur http://localhost:8080
```

✅ **Vérification:** Le serveur WebSocket écoute sur `ws://localhost:8080/chat`

### 3️⃣ Démarrer le Frontend (Angular)

```bash
cd chat-app-angular

# Installer les dépendances
npm install

# Lancer le serveur de développement
npm start

# L'application se lance sur http://localhost:4200
```

✅ **L'application est prête!** Ouvrez [http://localhost:4200](http://localhost:4200) dans votre navigateur.

---

## 💻 Utilisation

1. **Ouvrez l'application** sur [http://localhost:4200](http://localhost:4200)
2. **Écrivez un message** dans le champ de saisie
3. **Cliquez sur "Envoyer"** ou appuyez sur Entrée
4. **Regardez la magie opérer** - Les messages apparaissent en temps réel!
5. **Ouvrez un nouvel onglet** pour simuler plusieurs utilisateurs

### Exemple de flux d'interaction

```
User A                          User B
  │                               │
  ├─── "Bonjour!" ──WebSocket──> ││
  │                              │ ├─ Message reçu
  │    ◄────── "Salut!" ─────────┤
  │                               │
```

---

## 📡 Flux de Communication WebSocket

### Configuration serveur (WebSocketConfig.java)

- **Endpoint:** `/chat` 
- **Message broker:** `/topic`
- **Application prefix:** `/app`
- **Origines autorisées:** `http://localhost:4200`

### Flux d'une session

```
Angular Client (4200)          Spring Boot (8080)
       │                              │
       ├─ SockJS Connection ──────────>
       ├─ STOMP Subscribe /topic ───────>
       |                              │
       ├─ Send Message /app/chat ────>├─ Process
       │                              │
       <──── Broadcast /topic ────────┤
       │ (reçu par tous)              │
```

---

## 🔧 Configuration

### Backend - application.properties

```properties
server.port=8080
spring.application.name=chat-server
```

### Frontend - Connexion WebSocket (chat.component.ts)

```typescript
// Configuration actuelle
{
  webSocketFactory: () => new SockJS('http://localhost:8080/chat')
}
```

**⚠️ Adaptez l'URL du serveur selon votre environnement (dev, prod)**

---

## 📦 Scripts disponibles

### Frontend Angular

```bash
npm start              # Démarrer le serveur de développement
npm run build         # Build pour la production
npm test              # Lancer les tests unitaires
npm run watch         # Watch mode pour le développement
```

### Backend Spring Boot

```bash
mvn clean install     # Construire le projet
mvn spring-boot:run  # Lancer l'application
mvn test             # Exécuter les tests
```

---

## 🧪 Tests

### Tests Angular

```bash
cd chat-app-angular
npm test              # Tests unitaires avec Karma
```

### Tests Spring Boot

```bash
cd chat-server
mvn test
```

---

## 🐛 Dépannage

| Problème | Solution |
|----------|----------|
| **Connexion WebSocket refusée** | Vérifiez que le backend tourne sur `http://localhost:8080` |
| **CORS Error** | Vérifiez `WebSocketConfig.java` - `setAllowedOrigins("http://localhost:4200")` |
| **Port 8080 déjà utilisé** | Changez le port dans `application.properties` ou tuez le processus |
| **Port 4200 déjà utilisé** | `ng serve --port 4300` |
| **Messages ne s'affichent pas** | Ouvrez la console (`F12`) et vérifiez les erreurs WebSocket |

---

## 📚 Ressources Utiles

- [Documentation Angular 18](https://angular.dev)
- [Spring Boot WebSocket Guide](https://spring.io/guides/gs/messaging-stomp-websocket/)
- [STOMP over WebSocket](https://stomp.github.io/)
- [SockJS Documentation](https://github.com/sockjs/sockjs-client)

---

## 🤝 Contribuer

Les contributions sont les bienvenues! Pour proposer des améliorations:

1. Fork le projet
2. Créez une branche (`git checkout -b feature/amelioration`)
3. Commitez vos changements (`git commit -m 'Ajout de la feature'`)
4. Poussez vers la branche (`git push origin feature/amelioration`)
5. Ouvrez une Pull Request

### Idées d'améliorations

- [ ] Authentification utilisateur
- [ ] Persistance des messages (base de données)
- [ ] Salons de chat multiples
- [ ] Notifications en temps réel
- [ ] Upload d'images/fichiers
- [ ] Historique des messages
- [ ] Dark/Light theme
- [ ] Indicateur "en train de taper"

---

## 📄 Licence

Ce projet est sous licence MIT. Consultez le fichier [LICENSE](LICENSE) pour plus de détails.

2. **Configure the application:**
   - Ensure \`src/main/resources/application.properties\` is correctly configured for WebSocket.

3. **Run the Spring Boot server:**
   \`\`\`bash
   mvn spring-boot:run
   \`\`\`
   The server should now be running on **http://localhost:8080**.

### **3. Setting Up the Frontend (Angular)**

1. **Navigate to the Angular project directory:**
   \`\`\`bash
   cd ../chat-app-angular
   \`\`\`

2. **Install dependencies:**
   \`\`\`bash
   npm install
   \`\`\`

3. **Run the Angular app:**
   \`\`\`bash
   ng serve
   \`\`\`
   The frontend should now be running on **http://localhost:4200**.

---

## **Usage**

- Open multiple browser tabs or windows and navigate to **http://localhost:4200**.
- Send messages in one window, and they will appear in real-time on all other open instances of the application.

---

## **Configuration Details**

### **WebSocket Configuration**

The WebSocket connection is established via the \`/chat\` endpoint on the Spring Boot server. Ensure CORS settings in \`ChatServerApplication.java\` allow requests from **http://localhost:4200**.

\`\`\`java
cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://localhost:4200");
\`\`\`

