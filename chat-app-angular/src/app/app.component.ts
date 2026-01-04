import { Component } from '@angular/core';
import { ChatComponent } from './chat/chat.component';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [ChatComponent, FormsModule] // Add FormsModule here
})
export class AppComponent {
  title = 'chat-app-angular';
}
