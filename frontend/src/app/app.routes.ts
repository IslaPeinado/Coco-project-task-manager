import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';
import { AuthLayoutComponent } from './layout/auth-layout.component';
import { MainLayoutComponent } from './layout/main-layout.component';

export const routes: Routes = [
  {
    path: 'auth',
    component: AuthLayoutComponent,
    loadChildren: () =>
      import('./features/auth/auth.routes').then((m) => m.AUTH_ROUTES),
  },
  {
    path: '',
    component: MainLayoutComponent,
    canActivateChild: [authGuard],
    children: [
      {
        path: 'Inicio',
        loadChildren: () =>
          import('./features/home/home.routes').then((m) => m.HOME_ROUTES),
      },
      {
        path: 'Tareas',
        loadChildren: () =>
          import('./features/tasks/tasks.routes').then((m) => m.TASKS_ROUTES),
      },
      {
        path: 'Proyectos',
        loadChildren: () =>
          import('./features/projects/projects.routes').then(
            (m) => m.PROJECTS_ROUTES,
          ),
      },
      {
        path: 'Ususrios',
        loadChildren: () =>
          import('./features/users/users.routes').then((m) => m.USERS_ROUTES),
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'Inicio',
      },
    ],
  },
  {
    path: '**',
    redirectTo: 'Inicio',
  },
];
