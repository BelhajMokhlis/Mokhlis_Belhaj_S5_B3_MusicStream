import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Album } from '../models/album.model';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  private apiUrl = 'http://localhost:8080/api/users/albums';

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

  getAudioFile(albumId: string): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${albumId}/audio`, {
      responseType: 'blob'
    });
  }

  playAudio(audioUrl: string): Observable<Blob> {
    return this.http.get(audioUrl, {
      responseType: 'blob'
    });
  }

  addAlbum(album: Album, audioFile: File): Observable<Album> {
    const formData = new FormData();
    formData.append('album', JSON.stringify(album));
    formData.append('audioFile', audioFile);
    return this.http.post<Album>(this.apiUrl, formData);
  }

  updateAlbum(album: Album, audioFile?: File): Observable<Album> {
    const formData = new FormData();
    formData.append('album', JSON.stringify(album));
    if (audioFile) {
      formData.append('audioFile', audioFile);
    }
    return this.http.put<Album>(`${this.apiUrl}/${album.id}`, formData);
  }

  deleteAlbum(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchAlbums(query: string): Observable<Album[]> {
    return this.http.get<Album[]>(`${this.apiUrl}/search?q=${query}`);
  }

  getAlbumDetails(id: string): Observable<Album> {
    return this.http.get<Album>(`${this.apiUrl}/albums/${id}`);
  }
} 
