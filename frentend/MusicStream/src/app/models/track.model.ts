import { DurationPipe } from "../pipes/duration.pipe";

export interface Album {
  id: string;
  title: string;
  artist: string;
  releaseYear: number;
  genre: string;
}

export interface TrackMetadata {
  id: string;
  title: string;
  duration: string;
  trackNumber: number;
  description: string;
  categorie: string;
  audioFileId: string;
  dateAjout: Date;
  album: Album;
}

export interface Track {
  metadata: TrackMetadata;
  audioUrl: string;
} 