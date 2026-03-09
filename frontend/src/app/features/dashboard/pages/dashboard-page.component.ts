import { Component } from '@angular/core';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  template: `
    <section class="page">
      <h1>Dashboard</h1>
      <p>Resumen general de trabajo.</p>
    </section>
  `,
})
export class DashboardPageComponent {}
