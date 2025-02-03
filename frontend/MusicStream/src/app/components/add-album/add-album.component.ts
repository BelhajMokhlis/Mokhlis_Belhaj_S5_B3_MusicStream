import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AlbumService } from '../../services/album.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-album',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './add-album.component.html',
  styleUrl: './add-album.component.scss'
})
export class AddAlbumComponent {
  @Output() albumAdded = new EventEmitter<void>();
  albumForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private albumService: AlbumService,
    private router: Router
  ) {
    this.albumForm = this.formBuilder.group({
      title: ['', [Validators.required]],
      artist: ['', [Validators.required]],
      releaseYear: ['', [Validators.required, Validators.min(1900), Validators.max(new Date().getFullYear())]],
      genre: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.albumForm.valid) {
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
