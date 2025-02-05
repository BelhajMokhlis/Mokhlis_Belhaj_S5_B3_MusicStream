import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionAlbumsComponent } from './gestion-albums.component';

describe('GestionAlbumsComponent', () => {
  let component: GestionAlbumsComponent;
  let fixture: ComponentFixture<GestionAlbumsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionAlbumsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GestionAlbumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
