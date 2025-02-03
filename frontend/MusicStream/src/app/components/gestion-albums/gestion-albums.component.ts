import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlbumService } from '../../services/album.service';
import { Album } from '../../models/album.model';
import { FormsModule } from '@angular/forms';
import { AddAlbumComponent } from '../add-album/add-album.component';

@Component({
  selector: 'app-gestion-albums',
  standalone: true,
  imports: [CommonModule, FormsModule, AddAlbumComponent],
  templateUrl: './gestion-albums.component.html',
  styleUrl: './gestion-albums.component.scss'
})
export class GestionAlbumsComponent implements OnInit {
  showAddAlbum = false;
  showEditAlbum = false;
  albums: Album[] = [];
  filteredAlbums: Album[] = [];
  paginatedAlbums: Album[] = [];
  searchTitle: string = '';
  currentPage: number = 0;
  pageSize: number = 5;
  totalPages: number = 0;
  selectedAlbum: Album | null = null;

  constructor(private albumService: AlbumService) {}

  ngOnInit(): void {
    this.albumService.getAlbums().subscribe(albums => {
      this.albums = albums;
      this.filterAlbums();
    });
  }

  filterAlbums() {
    this.filteredAlbums = this.albums.filter(album =>
      album.title.toLowerCase().includes(this.searchTitle.toLowerCase())
    );
    this.totalPages = Math.ceil(this.filteredAlbums.length / this.pageSize);
    this.currentPage = 0;
    this.updatePaginatedAlbums();
  }

  updatePaginatedAlbums() {
    const start = this.currentPage * this.pageSize;
    const end = start + this.pageSize;
    this.paginatedAlbums = this.filteredAlbums.slice(start, end);
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.updatePaginatedAlbums();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.updatePaginatedAlbums();
    }
  }

  onPageSizeChange() {
    this.totalPages = Math.ceil(this.filteredAlbums.length / this.pageSize);
    this.currentPage = 0;
    this.updatePaginatedAlbums();
  }

  toggleAddAlbum() {
    this.showAddAlbum = !this.showAddAlbum;
  }

  deleteAlbum(id: string) {
    this.albumService.deleteAlbum(id).subscribe(() => {
      this.albums = this.albums.filter(album => album.id !== id);
      this.filterAlbums();
    });
  }

  editAlbum(album: Album) {
    this.selectedAlbum = album;
    this.showEditAlbum = true;
  }

  closeModal() {
    this.showAddAlbum = false;
    this.showEditAlbum = false;
    this.selectedAlbum = null;
  }

  onAlbumAdded() {
    this.closeModal();
    this.loadAlbums();
  }

  onAlbumEdited() {
    this.closeModal();
    this.loadAlbums();
  }

  loadAlbums() {
    this.albumService.getAlbums().subscribe(albums => {
      this.albums = albums;
      this.filterAlbums();
    });
  }
}
