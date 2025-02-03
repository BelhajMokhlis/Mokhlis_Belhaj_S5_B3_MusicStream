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

  addTrack(track: Track, audioFile: File): Observable<Track> {
    const formData = new FormData();
    formData.append('track', JSON.stringify(track));
    formData.append('audioFile', audioFile);
    return this.http.post<Track>(this.apiUrl, formData);
  }

  updateTrack(track: Track, audioFile?: File): Observable<Track> {
    const formData = new FormData();
    formData.append('track', JSON.stringify(track));
    if (audioFile) {
      formData.append('audioFile', audioFile);
    }
    return this.http.put<Track>(`${this.apiUrl}/${track.metadata.id}`, formData);
  }

  deleteTrack(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchTracks(query: string): Observable<Track[]> {
    return this.http.get<Track[]>(`${this.apiUrl}/search?q=${query}`);
  }

  setCurrentTrack(track: Track) {
    this.currentTrackSubject.next(track);
  }
} 