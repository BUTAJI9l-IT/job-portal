import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicantApplicationsComponent } from './applicant-applications.component';

describe('ApplicantApplicationsComponent', () => {
  let component: ApplicantApplicationsComponent;
  let fixture: ComponentFixture<ApplicantApplicationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ApplicantApplicationsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApplicantApplicationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
