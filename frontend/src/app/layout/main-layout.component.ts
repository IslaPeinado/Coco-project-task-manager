import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  styleUrl: './main-layout.component.css',
  template: `
    <header class="layout-header">
      <nav>
        <a routerLink="/home" routerLinkActive="active">Home</a>
        <a routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
        <a routerLink="/tasks" routerLinkActive="active">Tasks</a>
        <a routerLink="/projects" routerLinkActive="active">Projects</a>
        <a routerLink="/users" routerLinkActive="active">Users</a>
      </nav>
    </header>
    <main>
      <router-outlet></router-outlet>
    </main>
  `,
})
export class MainLayoutComponent {}
