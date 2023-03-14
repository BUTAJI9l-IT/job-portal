import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAccountApplicantComponent } from './user-account-applicant.component';

describe('UserAccountApplicantComponent', () => {
  let component: UserAccountApplicantComponent;
  let fixture: ComponentFixture<UserAccountApplicantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserAccountApplicantComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserAccountApplicantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
