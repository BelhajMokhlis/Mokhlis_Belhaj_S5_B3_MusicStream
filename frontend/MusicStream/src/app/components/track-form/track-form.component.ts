import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AlbumService } from '../../services/album.service';
import { Album } from '../../models/album.model';
import { TrackService } from '../../services/track.service';
import { Track } from '../../models/track.model';
import { Store } from '@ngrx/store';
import * as TrackActions from '../../store/actions/track.actions';

@Component({
  selector: 'app-track-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './track-form.component.html',
  styleUrl: './track-form.component.scss'
})
export class TrackFormComponent implements OnInit {
  @Input() trackToEdit: Track | null = null;
  @Output() trackAdded = new EventEmitter<void>();
  @Output() trackEdited = new EventEmitter<void>();
  
  trackForm: FormGroup;
  albums: Album[] = [];
  selectedFile: File | null = null;
  isEditMode: boolean = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private albumService: AlbumService,
    private trackService: TrackService,
    private store: Store
  ) {
    this.trackForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(2)]],
      trackNumber: ['', [Validators.required, Validators.min(1)]],
      description: ['', [Validators.maxLength(500)]],
      categorie: ['', [Validators.required, Validators.minLength(2)]],
      albumId: ['', [Validators.required]],
      file: ['', []]
    });
  }

  get f() { return this.trackForm.controls; }

  ngOnInit() {
    this.albumService.getAlbums().subscribe(albums => {
      this.albums = albums;
    });

    if (this.trackToEdit) {
      this.isEditMode = true;
      this.trackForm.patchValue({
        title: this.trackToEdit.metadata.title,
        trackNumber: this.trackToEdit.metadata.trackNumber,
        description: this.trackToEdit.metadata.description,
        categorie: this.trackToEdit.metadata.categorie,
        albumId: this.trackToEdit.metadata.album.id
      });
      // Make file optional when editing
      this.trackForm.get('file')?.setValidators(null);
      this.trackForm.get('file')?.updateValueAndValidity();
    } else {
      this.trackForm.get('file')?.setValidators([Validators.required]);
      this.trackForm.get('file')?.updateValueAndValidity();
    }
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    this.selectedFile = file;
  }

  onSubmit() {
    // if (this.trackForm.invalid || (!this.isEditMode && !this.selectedFile)) {
    //   console.log("submit");
    //   return;
    // }
    
    const formData = new FormData();
    if (this.selectedFile) {
      formData.append('audioFile', this.selectedFile);
    }
    
    const trackData = {
      title: this.trackForm.get('title')?.value,
      trackNumber: this.trackForm.get('trackNumber')?.value,
      description: this.trackForm.get('description')?.value,
      categorie: this.trackForm.get('categorie')?.value,
      albumId: this.trackForm.get('albumId')?.value
    };
    
    formData.append('track', JSON.stringify(trackData));

    if (this.isEditMode && this.trackToEdit) {
      this.trackService.updateTrack(this.trackToEdit.metadata.id, formData).subscribe({
        next: () => {
          this.trackEdited.emit();
          this.resetForm();
        },
        error: (error) => {
          console.error('Error updating track:', error);
        }
      });
    } 
    else {
      if (!this.selectedFile) {
        return;
      }
      this.trackService.addTrack(this.selectedFile, trackData).subscribe({
        next: () => {
          this.trackAdded.emit();
          this.resetForm();
        },
        error: (error) => {
          console.error('Error adding track:', error);
        }
      });
    }
  }

  private resetForm() {
    this.submitted = false;
    this.trackForm.reset();
    this.selectedFile = null;
  }
}
