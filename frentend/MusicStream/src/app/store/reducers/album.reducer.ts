import { createReducer, on } from '@ngrx/store';
import { Album } from '../../models/album.model';
import * as AlbumActions from '../actions/album.actions';

export interface AlbumState {
  albums: Album[];
  loading: boolean;
  error: any;
}

export const initialState: AlbumState = {
  albums: [],
  loading: false,
  error: null
};

export const albumReducer = createReducer(
  initialState,
  on(AlbumActions.loadAlbums, state => ({
    ...state,
    loading: true,
    error: null
  })),
  on(AlbumActions.loadAlbumsSuccess, (state, { albums }) => ({
    ...state,
    albums: Array.isArray(albums) ? albums : [],
    loading: false,
    error: null
  })),
  on(AlbumActions.loadAlbumsFailure, (state, { error }) => ({
    ...state,
    albums: [],
    loading: false,
    error
  }))
); 