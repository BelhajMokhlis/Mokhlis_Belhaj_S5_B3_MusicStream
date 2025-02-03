import { Component, Output, EventEmitter, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AlbumService } from '../../services/album.service';
import { Router } from '@angular/router';
import { Album } from '../../models/album.model';

@Component({
  selector: 'app-add-album',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-album.component.html',
  styleUrl: './add-album.component.scss'
})
export class AddAlbumComponent implements OnInit {
  @Input() albumToEdit: Album | null = null;
  @Output() albumAdded = new EventEmitter<void>();
  @Output() albumEdited = new EventEmitter<void>();
  
  albumForm: FormGroup;
  isEditMode: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private albumService: AlbumService,
    private router: Router
  ) {
    this.albumForm = this.formBuilder.group({
      title: ['', [Validators.required]],
      artist: ['', [Validators.required]],
      releaseYear: ['', [Validators.required, Validators.min(1980), Validators.max(new Date().getFullYear())]],
      genre: ['', [Validators.required]]
    });
  }

  ngOnInit() {
    if (this.albumToEdit) {
      this.isEditMode = true;
      this.albumForm.patchValue({
        title: this.albumToEdit.title,
        artist: this.albumToEdit.artist,
        releaseYear: this.albumToEdit.releaseYear,
        genre: this.albumToEdit.genre
      });
    }
  }

  onSubmit() {
    if (this.albumForm.valid) {
      if (this.isEditMode && this.albumToEdit) {
        this.albumService.updateAlbum(this.albumToEdit.id, this.albumForm.value).subscribe({
          next: () => {
            this.albumEdited.emit();
            this.albumForm.reset();
          },
          error: (error) => {
            console.error('Error updating album:', error);
          }
        });
      } else {
        this.albumService.addAlbum(this.albumForm.value).subscribe({
          next: () => {
            this.albumAdded.emit();
            this.albumForm.reset();
          },
          error: (error) => {
            console.error('Error adding album:', error);
          }
        });
      }
    }
  }
}
