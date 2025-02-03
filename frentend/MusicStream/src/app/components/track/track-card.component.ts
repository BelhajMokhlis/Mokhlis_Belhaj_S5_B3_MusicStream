import { Component, Input } from '@angular/core';
import { Track } from '../../models/track.model';
import { DatePipe } from '@angular/common';
import { DurationPipe } from '../../pipes/duration.pipe';
import { MatDialog } from '@angular/material/dialog';
import { TrackDetailComponent } from '../track-detail/track-detail.component';
import { TrackPlayerService } from '../../services/track-player.service';

@Component({
  selector: 'app-track-card',
  templateUrl: './track-card.component.html',
  standalone: true,
  imports: [DatePipe, DurationPipe]
})
export class TrackCardComponent {
  @Input() track!: Track;

  constructor(
    public dialog: MatDialog,
    private playerService: TrackPlayerService
  ) {}

  openTrackDetail(): void {
    this.dialog.open(TrackDetailComponent, {
      data: { track: this.track },
      width: '500px',
      position: { top: '50px' },
      autoFocus: true,
      enterAnimationDuration: '200ms',
      exitAnimationDuration: '200ms',
      panelClass: 'track-detail-dialog'
    });
  }

  playTrack(): void {
    this.playerService.playTrack(this.track);
  }
} 