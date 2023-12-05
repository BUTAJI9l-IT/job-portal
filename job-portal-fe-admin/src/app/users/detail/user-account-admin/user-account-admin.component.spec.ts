import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAccountAdminComponent } from './user-account-admin.component';

describe('UserAccountAdminComponent', () => {
  let component: UserAccountAdminComponent;
  let fixture: ComponentFixture<UserAccountAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserAccountAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserAccountAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
