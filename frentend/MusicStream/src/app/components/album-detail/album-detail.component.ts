import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlbumService } from '../../services/album.service';
import { Album } from '../../models/album.model';
import { TrackService } from '../../services/track.service';
import { Track } from '../../models/track.model';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { CommonModule } from '@angular/common';
import { TrackCardComponent } from '../track/track-card.component';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-album-detail',
  standalone: true,
  imports: [
    NavBarComponent,
    CommonModule,
    TrackCardComponent,
    FormsModule
  ],
  templateUrl: './album-detail.component.html',
  styleUrl: './album-detail.component.scss'
})
export class AlbumDetailComponent implements OnInit {
  album: Album | null = null;
  tracks: Track[] = [];
  filteredTracks: Track[] = [];
  searchQuery: string = '';
  albumId: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private albumService: AlbumService,
    private trackService: TrackService
  ) {}

  ngOnInit(): void {
    this.albumId = this.route.snapshot.paramMap.get('id');
    if (this.albumId) {
      this.albumService.getAlbumDetails(this.albumId).subscribe(album => {
        this.album = album;
      });

      this.trackService.getTracksByAlbumId(this.albumId).subscribe(
        (tracks) => {
          this.tracks = tracks;
          this.filteredTracks = tracks;
        }
      );
    }
  }

  filterTracks() {
    if (!this.searchQuery.trim()) {
      this.filteredTracks = this.tracks;
      return;
    }

    this.filteredTracks = this.tracks.filter(track => 
      track.metadata.title.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }
}
