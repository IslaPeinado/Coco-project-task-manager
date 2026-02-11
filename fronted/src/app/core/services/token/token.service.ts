import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenService {
  private readonly accessTokenKey = 'access_token';

  getAccessToken(): string | null {
    return localStorage.getItem(this.accessTokenKey);
  }

  setAccessToken(token: string): void {
    localStorage.setItem(this.accessTokenKey, token);
  }

  clearAccessToken(): void {
    localStorage.removeItem(this.accessTokenKey);
  }
}
