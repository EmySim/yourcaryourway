# **Chat Application (Angular + Spring Boot)**

A real-time chat application built using **Angular** for the frontend and **Spring Boot** for the backend. Users can exchange messages in real-time, with the Angular app handling the UI and WebSocket-based communication with the Spring Boot server.

---

## **Features**

- Real-time messaging between users
- Message display on the right for the sender and on the left for other users
- WebSocket-based communication between Angular frontend and Spring Boot backend

---

## **Prerequisites**

To run this project, ensure you have the following installed:

- **Node.js** (v12 or above)
- **Angular CLI** (v14 or above)
- **Java** (v11 or above)
- **Maven** (for building and running the Spring Boot backend)

---

## **Project Structure**

The repository is organized into two main folders:

- **`chat-app-angular`**: Contains the Angular frontend code.
- **`chat-server`**: Contains the Spring Boot backend code.

---

## **Setup Instructions**

### **1. Clone the Repository**

\`\`\`bash
git clone https://github.com/rutuja-nemane/Application-frameworks-assignment.git
cd chat-angular-app
\`\`\`

### **2. Setting Up the Backend (Spring Boot)**

1. **Navigate to the backend directory:**
   \`\`\`bash
   cd chat-server
   \`\`\`

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

