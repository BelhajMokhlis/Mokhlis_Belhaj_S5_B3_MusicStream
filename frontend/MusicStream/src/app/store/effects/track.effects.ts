import { Injectable } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { of } from 'rxjs';
import { map, mergeMap, catchError, switchMap } from 'rxjs/operators';
import { TrackService } from '../../services/track.service';
import * as TrackActions from '../actions/track.actions';

@Injectable()
export class TrackEffects {
  loadTracks$ = createEffect(() =>
    this.actions$.pipe(
      ofType(TrackActions.loadTracks),
      mergeMap(() => this.trackService.getAllTracks()
        .pipe(
          map(tracks => TrackActions.loadTracksSuccess({ tracks })),
          catchError(error => of(TrackActions.loadTracksFailure({ error })))
        ))
    )
  );

  addTrack$ = createEffect(() =>
    this.actions$.pipe(
      ofType(TrackActions.addTrack),
      switchMap(({ track, audioFile }) => {
        const formData = new FormData();
        formData.append('track', JSON.stringify(track));
        if (audioFile) {
          formData.append('file', audioFile);
        }
        return this.trackService.addTrack(audioFile, track).pipe(
          map(newTrack => TrackActions.addTrackSuccess({ track: newTrack })),
          catchError(error => of(TrackActions.addTrackFailure({ error })))
        );
      })
    )
  );

  updateTrack$ = createEffect(() =>
    this.actions$.pipe(
      ofType(TrackActions.updateTrack),
      switchMap(({ track, audioFile }) => {
        const formData = new FormData();
        formData.append('track', JSON.stringify(track));
        if (audioFile) {
          formData.append('file', audioFile);
        }
        return this.trackService.updateTrack(track.metadata.id, formData).pipe(
          map(updatedTrack => TrackActions.updateTrackSuccess({ track: updatedTrack })),
          catchError(error => of(TrackActions.updateTrackFailure({ error })))
        );
      })
    )
  );

  deleteTrack$ = createEffect(() =>
    this.actions$.pipe(
      ofType(TrackActions.deleteTrack),
      mergeMap(({ id }) =>
        this.trackService.deleteTrack(id).pipe(
          map(() => TrackActions.deleteTrackSuccess({ id })),
          catchError(error => of(TrackActions.deleteTrackFailure({ error })))
        )
      )
    )
  );

  constructor(
    private actions$: Actions,
    private trackService: TrackService
  ) {}
} 