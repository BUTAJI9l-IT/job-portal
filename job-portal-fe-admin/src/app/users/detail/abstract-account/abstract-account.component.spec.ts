import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbstractAccountComponent } from './abstract-account.component';

describe('AbstractAccountComponent', () => {
  let component: AbstractAccountComponent;
  let fixture: ComponentFixture<AbstractAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AbstractAccountComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AbstractAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
