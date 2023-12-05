import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RadioCompanySizeComponent } from './radio-company-size.component';

describe('RadioCompanySizeComponent', () => {
  let component: RadioCompanySizeComponent;
  let fixture: ComponentFixture<RadioCompanySizeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RadioCompanySizeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RadioCompanySizeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
