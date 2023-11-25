import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobSelectComponent } from './job-select.component';

describe('JobSelectComponent', () => {
  let component: JobSelectComponent;
  let fixture: ComponentFixture<JobSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [JobSelectComponent]
    });
    fixture = TestBed.createComponent(JobSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
