import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, mergeMap, catchError } from 'rxjs/operators';
import { AlbumService } from '../../services/album.service';
import * as AlbumActions from '../actions/album.actions';

@Injectable()
export class AlbumEffects {
  loadAlbums$ = createEffect(() => 
    this.actions$.pipe(
      ofType(AlbumActions.loadAlbums),
      mergeMap(() => this.albumService.getAlbums()
        .pipe(
          map(albums => AlbumActions.loadAlbumsSuccess({ albums })),
          catchError(error => of(AlbumActions.loadAlbumsFailure({ error })))
        ))
    )
  );

  constructor(
    private actions$: Actions,
    private albumService: AlbumService
  ) {}
} 