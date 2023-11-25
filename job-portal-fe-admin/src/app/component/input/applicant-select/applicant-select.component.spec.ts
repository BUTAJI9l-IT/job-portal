import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplicantSelectComponent } from './applicant-select.component';

describe('ApplicantSelectComponent', () => {
  let component: ApplicantSelectComponent;
  let fixture: ComponentFixture<ApplicantSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApplicantSelectComponent]
    });
    fixture = TestBed.createComponent(ApplicantSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
