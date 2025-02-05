import { Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { 
    path: 'home', 
    component: HomeComponent, 
    canActivate: [AuthGuard] 
  },
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        redirectTo: 'albums',
        pathMatch: 'full'
      },
      {
        path: 'albums',
        loadChildren: () => import('./components/gestion-albums/gestion-albums.module')
          .then(m => m.GestionAlbumsModule)
      },
      {
        path: 'tracks',
        loadChildren: () => import('./components/gestion-tracks/gestion-tracks.module')
          .then(m => m.GestionTracksModule)
      }
    ]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];
