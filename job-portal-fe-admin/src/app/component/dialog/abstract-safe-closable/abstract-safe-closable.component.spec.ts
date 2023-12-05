import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AbstractSafeClosableComponent } from './abstract-safe-closable.component';

describe('AbstractSafeClosableComponent', () => {
  let component: AbstractSafeClosableComponent;
  let fixture: ComponentFixture<AbstractSafeClosableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AbstractSafeClosableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AbstractSafeClosableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
