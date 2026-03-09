import { Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login-page.component';

export const AUTH_ROUTES: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login',
  },
  {
    path: 'login',
    component: LoginPageComponent,
  },
];
