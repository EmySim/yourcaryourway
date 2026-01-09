import { Component } from '@angular/core';
import { ChatComponent } from './chat/chat.component';
import { FormsModule } from '@angular/forms';

/**
 * AppComponent - Composant racine de l'application
 * Conteneur principal qui affiche le composant ChatComponent
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [ChatComponent, FormsModule] // Importe les modules nécessaires
})
export class AppComponent {
  title = 'chat-app-angular';

  constructor() {
    console.log('[AppComponent] Initialisation de l\'application');
  }
}
