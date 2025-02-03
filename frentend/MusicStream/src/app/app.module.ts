import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/auth/login/login.component';
import { AuthService } from './services/auth/auth.service';
import { authInterceptor } from './interceptor/auth.interceptor';
import { RouterModule } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { StoreModule } from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { trackReducer } from './store/reducers/track.reducer';
import { TrackEffects } from './store/effects/track.effects';
import { TrackCardComponent } from './components/track/track-card.component';
import { TrackListComponent } from './components/track/track-list.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatDialogModule } from '@angular/material/dialog';
import { albumReducer } from './store/reducers/album.reducer';
import { AlbumEffects } from './store/effects/album.effects';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    TrackListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    AppRoutingModule,
    RouterModule,
    StoreModule.forRoot({ tracks: trackReducer, albums: albumReducer }),
    EffectsModule.forRoot([TrackEffects, AlbumEffects]),
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: false }),
    TrackCardComponent,
    MatDialogModule,
    AppComponent,
    LoginComponent,
    RegisterComponent,
    FormsModule
  ],
  providers: [
    AuthService,
    { provide: HTTP_INTERCEPTORS, useValue: authInterceptor, multi: true },
    provideAnimationsAsync()
  ]
})
export class AppModule { } 