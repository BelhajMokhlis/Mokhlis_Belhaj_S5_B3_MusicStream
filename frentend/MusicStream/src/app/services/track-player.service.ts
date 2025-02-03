import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Track } from '../models/track.model';
import { Store } from '@ngrx/store';

interface Progress {
  currentTime: string;
  duration: string;
  progress: number;
}

@Injectable({
  providedIn: 'root'
})
export class TrackPlayerService {
  private audio = new Audio();
  private currentTrackSubject = new BehaviorSubject<Track | null>(null);
  private isPlayingSubject = new BehaviorSubject<boolean>(false);
  private progressSubject = new BehaviorSubject<Progress>({
    currentTime: '0:00',
    duration: '0:00',
    progress: 0
  });
  private volumeSubject = new BehaviorSubject<number>(100);
  private tracksSubject = new BehaviorSubject<Track[]>([]);

  currentTrack$ = this.currentTrackSubject.asObservable();
  isPlaying$ = this.isPlayingSubject.asObservable();
  progress$ = this.progressSubject.asObservable();
  volume$ = this.volumeSubject.asObservable();

  constructor(private store: Store) {
    this.setupAudioListeners();
  }

  setTracks(tracks: Track[]): void {
    this.tracksSubject.next(tracks);
  }

  loadTrack(track: Track): void {
    this.currentTrackSubject.next(track);
    this.audio.src = track.audioUrl;
    this.audio.load();
  }

  togglePlay(): void {
    if (this.audio.paused) {
      this.audio.play();
      this.isPlayingSubject.next(true);
    } else {
      this.audio.pause();
      this.isPlayingSubject.next(false);
    }
  }

  playNextTrack(): void {
    const tracks = this.tracksSubject.value;
    const currentTrack = this.currentTrackSubject.value;
    if (!tracks.length || !currentTrack) return;

    const currentIndex = tracks.findIndex(t => t.metadata.id === currentTrack.metadata.id);
    const nextIndex = (currentIndex + 1) % tracks.length;
    this.loadTrack(tracks[nextIndex]);
    this.togglePlay();
  }

  playPreviousTrack(): void {
    const tracks = this.tracksSubject.value;
    const currentTrack = this.currentTrackSubject.value;
    if (!tracks.length || !currentTrack) return;

    const currentIndex = tracks.findIndex(t => t.metadata.id === currentTrack.metadata.id);
    const prevIndex = currentIndex === 0 ? tracks.length - 1 : currentIndex - 1;
    this.loadTrack(tracks[prevIndex]);
    this.togglePlay();
  }

  seekTo(percentage: number): void {
    const time = (percentage / 100) * this.audio.duration;
    this.audio.currentTime = time;
  }

  setVolume(volume: number): void {
    this.audio.volume = volume / 100;
    this.volumeSubject.next(volume);
  }

  toggleMute(): void {
    if (this.audio.volume > 0) {
      this.audio.volume = 0;
      this.volumeSubject.next(0);
    } else {
      this.audio.volume = 1;
      this.volumeSubject.next(100);
    }
  }

  playTrack(track: Track): void {
    this.loadTrack(track);
    this.audio.play();
    this.isPlayingSubject.next(true);
  }

  private setupAudioListeners(): void {
    this.audio.addEventListener('timeupdate', () => {
      if (this.audio.duration) {
        this.progressSubject.next({
          currentTime: this.formatTime(this.audio.currentTime),
          duration: this.formatTime(this.audio.duration),
          progress: (this.audio.currentTime / this.audio.duration) * 100
        });
      }
    });

    this.audio.addEventListener('ended', () => {
      this.isPlayingSubject.next(false);
      this.playNextTrack();
    });
  }

  private formatTime(seconds: number): string {
    const mins = Math.floor(seconds / 60);
    const secs = Math.floor(seconds % 60);
    return `${mins}:${secs.toString().padStart(2, '0')}`;
  }

  cleanup(): void {
    this.currentTrackSubject.next(null);
    this.isPlayingSubject.next(false);
    if (this.audio) {
      this.audio.pause();
      this.audio.currentTime = 0;
    }
  }
}