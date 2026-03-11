import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import {Navbar} from '../shared/components/navbar/navbar';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, Navbar],
  styleUrl: './main-layout.component.css',
  template: `
    <header class="layout-header">
      <app-navbar></app-navbar>
    </header>
    <main>
      <router-outlet></router-outlet>
    </main>
  `,
})
export class MainLayoutComponent {}
