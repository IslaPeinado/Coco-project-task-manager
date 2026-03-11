import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-auth-layout',
  standalone: true,
  imports: [RouterOutlet],
  styleUrl: './auth-layout.component.css',
  template: `
    <main class="auth-layout">
      <router-outlet></router-outlet>
    </main>
  `,
})
export class AuthLayoutComponent {}
