import {ComponentFixture, TestBed} from '@angular/core/testing';

import {JobApplicantCardComponent} from './job-applicant-card.component';

describe('JobApplicantCardComponent', () => {
  let component: JobApplicantCardComponent;
  let fixture: ComponentFixture<JobApplicantCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JobApplicantCardComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(JobApplicantCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
