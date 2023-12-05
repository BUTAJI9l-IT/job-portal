import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IntermediateCheckComponent } from './intermediate-check.component';

describe('IntermediateCheckComponent', () => {
  let component: IntermediateCheckComponent;
  let fixture: ComponentFixture<IntermediateCheckComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IntermediateCheckComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IntermediateCheckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
