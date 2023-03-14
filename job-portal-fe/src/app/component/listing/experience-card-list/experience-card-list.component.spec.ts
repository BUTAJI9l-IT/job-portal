import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExperienceCardListComponent } from './experience-card-list.component';

describe('CardListComponent', () => {
  let component: ExperienceCardListComponent;
  let fixture: ComponentFixture<ExperienceCardListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExperienceCardListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExperienceCardListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
