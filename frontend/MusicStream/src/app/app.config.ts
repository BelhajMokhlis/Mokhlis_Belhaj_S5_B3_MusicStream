import { ApplicationConfig } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { provideStore } from '@ngrx/store';
import { provideHttpClient } from '@angular/common/http';
import { provideEffects } from '@ngrx/effects';
import { AlbumService } from './services/album.service';

import { routes } from './app.routes';
import { albumReducer } from './store/reducers/album.reducer';
import { trackReducer } from './store/reducers/track.reducer';
import { AlbumEffects } from './store/effects/album.effects';
import { TrackEffects } from './store/effects/track.effects';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(),
    provideStore({
      tracks: trackReducer,
      albums: albumReducer,
    }),
    provideEffects([AlbumEffects, TrackEffects]),
    provideRouter(routes, withComponentInputBinding()),
    AlbumService
  ]
};
