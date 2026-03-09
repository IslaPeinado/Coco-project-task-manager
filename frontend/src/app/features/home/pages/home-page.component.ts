import { Component } from '@angular/core';

@Component({
  selector: 'app-home-page',
  standalone: true,
  template: `
    <section class="page">
      <h1>Home</h1>
      <p>Frontend base de COCO inicializada en Angular 20.</p>
    </section>
  `,
  styles: [
    `
      .page {
        padding: 2rem;
      }
    `,
  ],
})
export class HomePageComponent {}
