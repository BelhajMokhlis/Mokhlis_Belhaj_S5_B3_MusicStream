import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Album } from '../models/album.model';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private apiUrl = 'http://localhost:8080/api/users/albums';
  private adminUrl = 'http://localhost:8080/api/admin/albums';

  constructor(private http: HttpClient) {}

  getAlbums(): Observable<Album[]> {
    return this.http.get<any>(this.apiUrl).pipe(
      map(response => {
        // If response is an object with a content property, return content
        // Otherwise, if it's already an array, return it directly
        return Array.isArray(response) ? response : response.content || [];
      })
    );
  }

  getAlbumById(id: string): Observable<Album> {
    return this.http.get<Album>(`${this.apiUrl}/${id}`);
  }

  getAllAlbums(): Observable<Album[]> {
    return this.http.get<Album[]>(this.apiUrl);
  }

  addAlbum(album: Album): Observable<Album> {
    return this.http.post<Album>(this.adminUrl, album);
  }

  updateAlbum(id: string, album: Album): Observable<any> {
    return this.http.put(`${this.adminUrl}/update?id=${id}`, album);
  }

  deleteAlbum(id: string): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/delete?id=${id}`);
  }

  getAlbumDetails(id: string): Observable<Album> {
    return this.http.get<Album>(`${this.apiUrl}/${id}`);
  }
} 
