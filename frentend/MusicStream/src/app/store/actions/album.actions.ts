import { createAction, props } from '@ngrx/store';
import { Album } from '../../models/album.model';

export const loadAlbums = createAction('[Album] Load Albums');
export const loadAlbumsSuccess = createAction(
  '[Album] Load Albums Success',
  props<{ albums: Album[] }>()
);
export const loadAlbumsFailure = createAction(
  '[Album] Load Albums Failure',
  props<{ error: any }>()
); 

export const addAlbum = createAction(
  '[Album] Add Album',
  props<{ album: Album }>()
);

export const addAlbumSuccess = createAction(
  '[Album] Add Album Success',
  props<{ album: Album }>()
);

export const addAlbumFailure = createAction(
  '[Album] Add Album Failure',
  props<{ error: any }>()
);

export const updateAlbum = createAction(
  '[Album] Update Album',
  props<{ album: Album }>()
);

