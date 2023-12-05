import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAccountCompanyComponent } from './user-account-company.component';

describe('UserAccountCompanyComponent', () => {
  let component: UserAccountCompanyComponent;
  let fixture: ComponentFixture<UserAccountCompanyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserAccountCompanyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserAccountCompanyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
