import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [RouterOutlet ],
  template: `
    <header class="layout-header">
      <p>Hola</p>
    </header>
    <main class="page">
      <router-outlet></router-outlet>
    </main>
  `,
})
export class MainLayoutComponent {}
