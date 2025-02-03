import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { GestionAlbumsComponent } from './gestion-albums.component';

const routes: Routes = [
  { path: '', component: GestionAlbumsComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    GestionAlbumsComponent
  ]
})
export class GestionAlbumsModule { } 