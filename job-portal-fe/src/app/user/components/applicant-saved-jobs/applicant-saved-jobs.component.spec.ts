import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicantSavedJobsComponent } from './applicant-saved-jobs.component';

describe('ApplicantSavedJobsComponent', () => {
  let component: ApplicantSavedJobsComponent;
  let fixture: ComponentFixture<ApplicantSavedJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicantSavedJobsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplicantSavedJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
