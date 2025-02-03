import { Routes } from '@angular/router';

export const DASHBOARD_ROUTES: Routes = [
  { 
    path: 'albums', 
    loadChildren: () => import('../gestion-albums/gestion-albums.module')
      .then(m => m.GestionAlbumsModule)
  },
  { 
    path: 'tracks', 
    loadChildren: () => import('../gestion-tracks/gestion-tracks.module')
      .then(m => m.GestionTracksModule)
  }
]; 