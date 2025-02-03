import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app/app-routing.module';
import { authInterceptor } from './app/interceptor/auth.interceptor';
import { provideStore } from '@ngrx/store';
import { provideEffects } from '@ngrx/effects';
import { trackReducer } from './app/store/reducers/track.reducer';
import { albumReducer } from './app/store/reducers/album.reducer';
import { TrackEffects } from './app/store/effects/track.effects';
import { AlbumEffects } from './app/store/effects/album.effects';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    provideRouter(routes),
    provideStore({ tracks: trackReducer, albums: albumReducer }),
    provideEffects([TrackEffects, AlbumEffects])
  ]
}).catch(err => console.error(err));
