import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionTracksComponent } from './gestion-tracks.component';

describe('GestionTracksComponent', () => {
  let component: GestionTracksComponent;
  let fixture: ComponentFixture<GestionTracksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionTracksComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GestionTracksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
