import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map, BehaviorSubject } from 'rxjs';
import { Track } from '../models/track.model';
import { Page } from '../models/page.model';

@Injectable({
  providedIn: 'root'
})
export class TrackService {
  private apiUrl = 'http://localhost:8080/api/users/chanson';
  private baseUrl = 'http://localhost:8080';
  private adminUrl = 'http://localhost:8080/api/admin/chansons';

  private currentTrackSubject = new BehaviorSubject<Track | null>(null);
  currentTrack$ = this.currentTrackSubject.asObservable();

  constructor(private http: HttpClient) {}

  getAllTracks(): Observable<Track[]> {
    return this.http.get<Page<Track>>(this.apiUrl)
      .pipe(
        map(response => response.content),
        map(tracks => tracks.map(track => ({
          ...track,
          audioUrl: `${this.baseUrl}${track.audioUrl}`
        })))
      );
  }

  getTracksByAlbumId(albumId: string): Observable<Track[]> {
    return this.http.get<Page<Track>>(`${this.apiUrl}/album/${albumId}`).pipe(
      map(response => response.content),
      map(tracks => tracks.map(track => ({
        ...track,
        audioUrl: `${this.baseUrl}${track.audioUrl}`
      })))
    );
  }

  getAudioFile(trackId: string): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${trackId}/audio`, {
      responseType: 'blob'
    });
  }

  playAudio(audioUrl: string): Observable<Blob> {
    return this.http.get(audioUrl, {
      responseType: 'blob'
    });
  }

  addTrack(file: File, otherData: any): Observable<Track> {
    const formData = new FormData();
    formData.append('file', file);
    // Add other form data as needed
    for (const key in otherData) {
      if (otherData.hasOwnProperty(key)) {
        formData.append(key, otherData[key]);
      }
    }
    return this.http.post<Track>(this.adminUrl, formData);
  }

  updateTrack(id: string, data: FormData): Observable<any> {
    const trackData = JSON.parse(data.get('track') as string);
    
    return this.http.put(`${this.adminUrl}/${id}`, trackData, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  deleteTrack(id: string): Observable<void> {
    return this.http.delete<void>(`${this.adminUrl}/${id}`);
  }

  searchTracks(query: string): Observable<Track[]> {
    return this.http.get<Track[]>(`${this.apiUrl}/search?q=${query}`);
  }

  setCurrentTrack(track: Track) {
    this.currentTrackSubject.next(track);
  }
} 