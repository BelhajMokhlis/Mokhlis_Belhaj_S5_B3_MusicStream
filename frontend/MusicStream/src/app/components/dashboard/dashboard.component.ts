import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterModule, NavBarComponent, CommonModule],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent {}
