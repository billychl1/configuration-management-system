import { Component } from '@angular/core';

/**
 * Root component of the application.
 * Serves as the main container for all other components.
 * Uses router-outlet for dynamic content rendering based on routes.
 */
@Component({
  selector: 'app-root',
  template: '<router-outlet></router-outlet>'
})
export class AppComponent { }