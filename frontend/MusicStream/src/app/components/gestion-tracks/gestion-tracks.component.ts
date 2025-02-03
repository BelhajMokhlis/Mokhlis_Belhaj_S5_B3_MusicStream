import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrackService } from '../../services/track.service';
import { Track } from '../../models/track.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-gestion-tracks',
  standalone: true,
  imports: [CommonModule, FormsModule],
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

  constructor(private trackService: TrackService) {}

  ngOnInit(): void {
    this.trackService.getAllTracks().subscribe((tracks: Track[]) => {
      this.tracks = tracks;
      this.filterTracks();
    });
  }

  filterTracks() {
    if (this.searchQuery) {
      this.filteredTracks = this.tracks.filter(track =>
        track.metadata.title.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.filteredTracks = this.tracks;
    }
    this.totalPages = Math.ceil(this.filteredTracks.length / this.pageSize);
    this.currentPage = 0;
    this.updatePaginatedTracks();
  }

  updatePaginatedTracks() {
    const start = this.currentPage * this.pageSize;
    const end = start + this.pageSize;
    this.filteredTracks = this.filteredTracks.slice(start, end);
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.updatePaginatedTracks();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.updatePaginatedTracks();
    }
  }

  onPageSizeChange() {
    this.totalPages = Math.ceil(this.tracks.length / this.pageSize);
    this.currentPage = 0;
    this.updatePaginatedTracks();
  }
}
