import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { GestionTracksComponent } from './gestion-tracks.component';

const routes: Routes = [
  { path: '', component: GestionTracksComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    GestionTracksComponent
  ]
})
export class GestionTracksModule { } 