import { Routes } from '@angular/router';
import { TasksPageComponent } from './pages/tasks-page.component';

export const TASKS_ROUTES: Routes = [
  {
    path: '',
    component: TasksPageComponent,
  },
];
