import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';

import { routes } from './app.routes';

/**
 * Configuration de l'application Angular
 * Définit les providers globaux de l'application
 */
export const appConfig: ApplicationConfig = {
  providers: [
    // Active la détection des changements optimisée
    provideZoneChangeDetection({ eventCoalescing: true }),
    // Configure le routeur de l'application
    provideRouter(routes),
    // Active le client HTTP pour les requêtes synchrones
    provideHttpClient()
  ]
};

console.log('[AppConfig] Configuration de l\'application chargée');
