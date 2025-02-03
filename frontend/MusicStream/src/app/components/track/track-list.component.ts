import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Track } from '../../models/track.model';
import { TrackService } from '../../services/track.service';
import * as TrackActions from '../../store/actions/track.actions';
import { selectAllTracks } from '../../store/selectors/track.selectors';

@Component({
  selector: 'app-track-list',
  template: `
    <div class="grid gap-4">
      <ng-container *ngIf="tracks$ | async as tracks">
        <app-track-card 
          *ngFor="let track of tracks"
          [track]="track"
          (click)="playTrack(track)"
        ></app-track-card>
      </ng-container>
    </div>
  `
})
export class TrackListComponent implements OnInit {
  tracks$ = this.store.select(selectAllTracks);
  
  constructor(
    private store: Store,
    private trackService: TrackService
  ) {}

  ngOnInit() {
    this.store.dispatch(TrackActions.loadTracks());
  }

  playTrack(track: Track) {
    if (track.audioUrl) {
      this.trackService.playAudio(track.audioUrl).subscribe(blob => {
        const audio = new Audio(URL.createObjectURL(blob));
        audio.play();
      });
    }
  }
} 