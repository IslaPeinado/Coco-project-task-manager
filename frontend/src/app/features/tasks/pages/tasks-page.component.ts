import { Component } from '@angular/core';

@Component({
  selector: 'app-tasks-page',
  standalone: true,
  styleUrl: './tasks-page.component.css',
  template: `
    <section class="page">
      <h1>Tasks</h1>
      <p>Ruta base para la gestión de tareas.</p>
    </section>
  `,
})
export class TasksPageComponent {}
