import { Component } from '@angular/core';

@Component({
  selector: 'app-tasks-page',
  standalone: true,
  template: `
    <section class="page">
      <h1>Tasks</h1>
      <p>Ruta base para la gestión de tareas.</p>
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
export class TasksPageComponent {}
