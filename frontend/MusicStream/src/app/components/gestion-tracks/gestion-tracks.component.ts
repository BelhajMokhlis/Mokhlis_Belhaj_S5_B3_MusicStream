import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrackService } from '../../services/track.service';
import { Track } from '../../models/track.model';
import { FormsModule } from '@angular/forms';
import { TrackFormComponent } from '../track-form/track-form.component';

@Component({
  selector: 'app-gestion-tracks',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    TrackFormComponent
  ],
  templateUrl: './gestion-tracks.component.html',
  styleUrl: './gestion-tracks.component.scss'
})
export class GestionTracksComponent implements OnInit {
  tracks: Track[] = [];
  filteredTracks: Track[] = [];
  searchQuery: string = '';
  currentPage: number = 0;
  pageSize: number = 5;
  totalPages: number = 0;
  showAddTrack = false;
  showEditTrack = false;
  selectedTrack: Track | null = null;

  constructor(private trackService: TrackService) {}

  ngOnInit(): void {
    this.loadTracks();
  }

  filterTracks() {
    let filtered = this.tracks;
    if (this.searchQuery) {
      filtered = this.tracks.filter(track =>
        track.metadata.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        track.metadata.album.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        track.metadata.album.artist.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
    this.totalPages = Math.ceil(filtered.length / this.pageSize);
    const start = this.currentPage * this.pageSize;
    const end = start + this.pageSize;
    this.filteredTracks = filtered.slice(start, end);
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.filterTracks();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.filterTracks();
    }
  }

  onPageSizeChange() {
    this.currentPage = 0;
    this.filterTracks();
  }

  toggleAddTrack() {
    this.showAddTrack = !this.showAddTrack;
    this.showEditTrack = false;
    this.selectedTrack = null;
  }

  closeModal() {
    this.showAddTrack = false;
    this.showEditTrack = false;
    this.selectedTrack = null;
  }

  editTrack(track: Track) {
    this.selectedTrack = track;
    this.showEditTrack = true;
    this.showAddTrack = false;
  }

  deleteTrack(track: Track) {
    if (confirm('Are you sure you want to delete this track?')) {
      this.trackService.deleteTrack(track.metadata.id).subscribe({
        next: () => {
          this.loadTracks();
        },
        error: (error) => {
          console.error('Error deleting track:', error);
        }
      });
    }
  }

  onTrackAdded() {
    this.closeModal();
    this.loadTracks();
  }

  onTrackEdited() {
    this.closeModal();
    this.loadTracks();
  }

  private loadTracks() {
    this.trackService.getAllTracks().subscribe({
      next: (tracks: Track[]) => {
        this.tracks = tracks;
        this.filterTracks();
      },
      error: (error) => {
        console.error('Error loading tracks:', error);
      }
    });
  }
}
