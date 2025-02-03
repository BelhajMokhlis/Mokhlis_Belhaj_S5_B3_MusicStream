import { createReducer, on } from '@ngrx/store';
import { Track } from '../../models/track.model';
import * as TrackActions from '../actions/track.actions';

export interface TrackState {
  tracks: Track[];
  loading: boolean;
  error: any;
  searchQuery: string;
  selectedTrackId: string | null;
}

export const initialState: TrackState = {
  tracks: [],
  loading: false,
  error: null,
  searchQuery: '',
  selectedTrackId: null
};

export const trackReducer = createReducer(
  initialState,
  
  on(TrackActions.loadTracks, state => ({ 
    ...state, 
    loading: true 
  })),
  
  on(TrackActions.loadTracksSuccess, (state, { tracks }) => ({ 
    ...state, 
    tracks,
    loading: false 
  })),
  
  on(TrackActions.loadTracksFailure, (state, { error }) => ({ 
    ...state, 
    error,
    loading: false 
  })),
  
  on(TrackActions.addTrackSuccess, (state, { track }) => ({
    ...state,
    tracks: [...state.tracks, track],
    loading: false
  })),
  
  on(TrackActions.updateTrackSuccess, (state, { track }) => ({
    ...state,
    tracks: state.tracks.map(t => t.metadata.id === track.metadata.id ? track : t),
    loading: false
  })),
  
  on(TrackActions.deleteTrackSuccess, (state, { id }) => ({
    ...state,
    tracks: state.tracks.filter(t => t.metadata.id !== id),
    loading: false
  }))
);

export const selectTrackState = (state: { tracks: TrackState }) => state.tracks;
export const selectAllTracks = (state: TrackState) => state.tracks;
export const selectTrackLoading = (state: TrackState) => state.loading;
export const selectTrackError = (state: TrackState) => state.error;
export const selectSearchQuery = (state: TrackState) => state.searchQuery; 