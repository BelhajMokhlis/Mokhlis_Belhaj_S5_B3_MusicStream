import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { jwtDecode } from 'jwt-decode';

interface JwtPayloadCustom {
  roles: { authority: string }[];
}

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(): boolean {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return false;
    }
    return true;
  }

  isAdmin(): boolean {
    const token = localStorage.getItem('token');
    if (!token) {
      return false;
    }
    try {
      const decodedToken = jwtDecode<JwtPayloadCustom>(token);
      const isAdmin = decodedToken.roles.some((role: { authority: string }) => role.authority === 'ROLE_ADMIN');
      return isAdmin;
    } catch (error) {
      console.error('Token decoding failed:', error);
      return false;
    }
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }
} 