import { Component } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthGuard } from '../../guards/auth.guard';
import { AuthService } from '../../services/auth/auth.service';
import { TrackPlayerService } from '../../services/track-player.service';

@Component({
  selector: 'app-nav-bar',
  standalone: true,
  imports: [RouterModule, CommonModule],
  template: `
    <div class="flex flex-col gap-4">
      <!-- Main navigation -->
      <div class="flex justify-between items-center p-4 text-white">
        <a [routerLink]="'/home'" class="text-4xl font-bold">Music Stream</a>
        <div class="flex gap-4 items-center">
          <a *ngIf="isAdmin" [routerLink]="'/dashboard'" class="text-2xl font-bold">Dashboard</a>
          <button class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-md transition duration-300" (click)="logout()">Logout</button>
        </div>
      </div>

      <!-- Dashboard navigation -->
      <nav *ngIf="isDashboardRoute()" class="bg-zinc-900 p-4 rounded-lg">
        <div class="flex space-x-4">
          <a [routerLink]="['/dashboard/albums']"
             routerLinkActive="bg-purple-700"
             class="px-4 py-2 rounded-md text-white hover:bg-purple-600 transition-colors duration-200 flex items-center space-x-2">
            <span class="material-icons">album</span>
            <span>Albums</span>
          </a>
          <a [routerLink]="['/dashboard/tracks']"
             routerLinkActive="bg-purple-700"
             class="px-4 py-2 rounded-md text-white hover:bg-purple-600 transition-colors duration-200 flex items-center space-x-2">
            <span class="material-icons">music_note</span>
            <span>Tracks</span>
          </a>
        </div>
      </nav>
    </div>
  `
})
export class NavBarComponent {
  isAdmin = false;

  constructor(
    private authGuard: AuthGuard,
    public router: Router,
    private authService: AuthService,
    private playerService: TrackPlayerService
  ) {
    this.isAdmin = this.authGuard.isAdmin();
  }

  isDashboardRoute(): boolean {
    return this.router.url.includes('/dashboard');
  }

  logout(): void {
    this.authService.logout();
    this.playerService.cleanup();
    this.router.navigate(['/login']);
  }
}
