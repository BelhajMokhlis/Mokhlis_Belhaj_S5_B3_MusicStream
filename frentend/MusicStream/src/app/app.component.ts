import { Component } from '@angular/core';
import { TrackPlayerComponent } from './components/track-player/track-player.component';
import { Observable } from 'rxjs';
import { Track } from './models/track.model';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    TrackPlayerComponent,
    RouterOutlet
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  tracks$: Observable<Track[]>;

  constructor() {
    this.tracks$ = new Observable<Track[]>();
  }
}
