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
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent {


  isAdmin = false;  // Set this appropriately
  constructor(
    private authGuard: AuthGuard,
    private router: Router,
    private authService: AuthService,
    private trackPlayerService: TrackPlayerService
  ) {
    this.isAdmin = this.authGuard.isAdmin();
  }
  logout() {
    // Add your logout logic here
    this.router.navigate(['/login']);
  }
}
