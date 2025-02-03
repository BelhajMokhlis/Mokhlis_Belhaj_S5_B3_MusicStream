import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { Track } from '../../models/track.model';
import { Observable, Subscription } from 'rxjs';
import { AsyncPipe, NgIf } from '@angular/common';
import { TrackPlayerService } from '../../services/track-player.service';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-track-player',
  standalone: true,
  imports: [AsyncPipe, NgIf],
  templateUrl: './track-player.component.html',
  styleUrl: './track-player.component.scss'
})
export class TrackPlayerComponent implements OnInit, OnDestroy {
  @Input() tracks$!: Observable<Track[]>;
  currentTrack$ = this.playerService.currentTrack$;
  isPlaying$ = this.playerService.isPlaying$;
  progress$ = this.playerService.progress$;
  volume$ = this.playerService.volume$;
  
  private tracksSubscription?: Subscription;

  constructor(
    private playerService: TrackPlayerService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.tracksSubscription = this.tracks$.subscribe(tracks => {
      this.playerService.setTracks(tracks);
    });
  }

  ngOnDestroy(): void {
    this.tracksSubscription?.unsubscribe();
    this.playerService.cleanup();
  }

  playPreviousTrack(): void {
    this.playerService.playPreviousTrack();
  }

  playNextTrack(): void {
    this.playerService.playNextTrack();
  }

  togglePlay(): void {
    this.playerService.togglePlay();
  }

  seekTo(event: any): void {
    this.playerService.seekTo(event.target.value);
  }

  toggleMute(): void {
    this.playerService.toggleMute();
  }

  setVolume(event: any): void {
    this.playerService.setVolume(event.target.value);
  }
}
