import { createFeatureSelector, createSelector } from '@ngrx/store';
import { AlbumState } from '../reducers/album.reducer';

export const selectAlbumState = createFeatureSelector<AlbumState>('albums');

export const selectAllAlbums = createSelector(
  selectAlbumState,
  (state: AlbumState) => state.albums
);

export const selectAlbumsLoading = createSelector(
  selectAlbumState,
  (state: AlbumState) => state.loading
);

export const selectAlbumsError = createSelector(
  selectAlbumState,
  (state: AlbumState) => state.error
); 