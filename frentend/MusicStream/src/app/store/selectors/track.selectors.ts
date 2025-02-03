import { createFeatureSelector, createSelector } from '@ngrx/store';
import { TrackState } from '../reducers/track.reducer';

export const selectTrackState = createFeatureSelector<TrackState>('tracks');

export const selectAllTracks = createSelector(
  selectTrackState,
  (state) => state.tracks
);

export const selectTrackError = createSelector(
  selectTrackState,
  (state) => state.error
);

export const selectTrackLoading = createSelector(
  selectTrackState,
  (state) => state.loading
); 