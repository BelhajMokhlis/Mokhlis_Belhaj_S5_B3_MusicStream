import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { AuthGuard } from './guards/auth.guard';
import { isAdminGuard } from './guards/is-admin.guard';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AlbumDetailComponent } from './components/album-detail/album-detail.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { 
    path: 'dashboard', 
    component: DashboardComponent, 
    canActivate: [AuthGuard,isAdminGuard],
    children: [
      {
        path: '',
        loadChildren: () => import('./components/dashboard/dashboard.module').then(m => m.DashboardModule)
      }
    ]
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'album/:id', component: AlbumDetailComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } 