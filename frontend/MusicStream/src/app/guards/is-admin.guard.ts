import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthGuard } from './auth.guard';

@Injectable({
  providedIn: 'root'
})
export class isAdminGuard implements CanActivate {
  constructor(
    private authGuard: AuthGuard,
    private router: Router
  ) {}

  canActivate(): boolean {
    if (!this.authGuard.isAdmin()) {
      this.router.navigate(['/home']);
      return false;
    }
    return true;
  }
} 