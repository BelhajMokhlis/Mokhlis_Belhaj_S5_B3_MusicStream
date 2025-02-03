import { Routes } from '@angular/router';

export const DASHBOARD_ROUTES: Routes = [
  {
    path: 'albums',
    loadComponent: () => import('../gestion-albums/gestion-albums.component')
      .then(m => m.GestionAlbumsComponent)
  },
  {
    path: 'tracks',
    loadComponent: () => import('../gestion-tracks/gestion-tracks.component')
      .then(m => m.GestionTracksComponent)
  },
  {
    path: '',
    redirectTo: 'tracks',
    pathMatch: 'full'
  }
]; 