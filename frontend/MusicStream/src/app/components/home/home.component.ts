import { Component, OnInit } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Track } from '../../models/track.model';
import * as TrackActions from '../../store/actions/track.actions';
import { selectAllTracks } from '../../store/selectors/track.selectors';
import { AsyncPipe, NgFor, NgIf } from '@angular/common';
import { TrackCardComponent } from '../track/track-card.component';
import { TrackPlayerComponent } from '../track-player/track-player.component';
import { AuthGuard } from '../../guards/auth.guard';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';
import { NavBarComponent } from '../nav-bar/nav-bar.component';
import { Album } from '../../models/album.model';
import { selectAllAlbums } from '../../store/selectors/album.selectors';
import { AlbumCardComponent } from '../album-card/album-card.component';
import * as AlbumActions from '../../store/actions/album.actions';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AlbumService } from '../../services/album.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: true,
  imports: [
    AsyncPipe, 
    NgFor, 
    NgIf, 
    NavBarComponent, 
    AlbumCardComponent,
    FormsModule
  ]
})
export class HomeComponent implements OnInit {
  tracks$: Observable<Track[]>;
  albums$: Observable<Album[]>;
  isAdmin: boolean = false;
  searchTerm: string = '';
  private albumsSubject = new BehaviorSubject<any[]>([]);
  filteredAlbums$ = this.albumsSubject.asObservable();

  constructor(
    private store: Store,
    private authGuard: AuthGuard,
    private router: Router,
    private albumService: AlbumService
  ) {
    this.tracks$ = this.store.select(selectAllTracks);
    this.albums$ = this.store.select(selectAllAlbums);
    this.isAdmin = this.authGuard.isAdmin();

    this.albums$.subscribe(albums => {
      console.log("albums", albums.length);
      this.albumsSubject.next(albums);
    });
  }

  ngOnInit(): void {
    this.store.dispatch(TrackActions.loadTracks());
    this.store.dispatch(AlbumActions.loadAlbums());
  }

  filterAlbums() {
    this.albums$.pipe(
      map(albums => {
        if (!this.searchTerm) return albums;
        return albums.filter(album => 
          album.title.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
          album.artist.toLowerCase().includes(this.searchTerm.toLowerCase())
        );
      })
    ).subscribe(filteredAlbums => {
      this.albumsSubject.next(filteredAlbums);
    });
  }
} 