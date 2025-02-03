import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard.component';
import { GestionAlbumsComponent } from '../gestion-albums/gestion-albums.component';
import { GestionTracksComponent } from '../gestion-tracks/gestion-tracks.component';
import { AlbumCardComponent } from '../album-card/album-card.component';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { DASHBOARD_ROUTES } from './dashboard.routes';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(DASHBOARD_ROUTES),
    DashboardComponent,
    GestionTracksComponent,
    AlbumCardComponent,
    NavBarComponent,
    GestionAlbumsComponent
  ]
})
export class DashboardModule { }
