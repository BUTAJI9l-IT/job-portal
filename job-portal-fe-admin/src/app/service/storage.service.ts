import {Injectable} from '@angular/core';
import {LoginResponse} from "../../model/loginResponse";
import {JwtClaims} from "../../model/jwtClaims";
import jwt_decode from 'jwt-decode';

const TOKEN_KEY = 'auth-token';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() {
  }

  clean(): void {
    window.sessionStorage.clear();
  }

  public saveTokens(tokens: LoginResponse): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, JSON.stringify(tokens));
  }

  public getTokens(): LoginResponse | null {
    const user = window.sessionStorage.getItem(TOKEN_KEY);
    if (user) {
      return JSON.parse(user);
    }

    return null;
  }

  public getTokenInfo(): JwtClaims | null {
    const user = window.sessionStorage.getItem(TOKEN_KEY);
    if (user) {
      const response: LoginResponse = JSON.parse(user);
      const token: string = response.accessToken!;
      return jwt_decode(token);
    }

    return null;
  }

  public isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(TOKEN_KEY);
    return !!user;
  }
}
