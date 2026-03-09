import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-auth-layout',
  standalone: true,
  imports: [RouterOutlet],
  template: `
    <main class="auth-layout">
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [
    `
      .auth-layout {
        min-height: 100vh;
        display: grid;
        place-items: center;
        padding: 2rem;
      }
    `,
  ],
})
export class AuthLayoutComponent {}
