import { Injectable } from '@angular/core';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  readonly apiBaseUrl = environment.API_BASE_URL;
  readonly loginEndpoint = `${this.apiBaseUrl}/auth/login`;
}
