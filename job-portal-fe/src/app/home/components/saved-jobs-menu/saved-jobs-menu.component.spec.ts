import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavedJobsMenuComponent } from './saved-jobs-menu.component';

describe('SavedJobsMenuComponent', () => {
  let component: SavedJobsMenuComponent;
  let fixture: ComponentFixture<SavedJobsMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SavedJobsMenuComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SavedJobsMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
