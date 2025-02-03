import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { DatePipe } from '@angular/common';
import { DurationPipe } from '../../pipes/duration.pipe';

@Component({
  selector: 'app-track-detail',
  standalone: true,
  imports: [DatePipe, DurationPipe, MatDialogModule],
  templateUrl: './track-detail.component.html'
})
export class TrackDetailComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<TrackDetailComponent>
  ) {}
}
