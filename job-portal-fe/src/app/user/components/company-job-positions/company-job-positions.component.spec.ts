import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyJobPositionsComponent } from './company-job-positions.component';

describe('CompanyJobPositionsComponent', () => {
  let component: CompanyJobPositionsComponent;
  let fixture: ComponentFixture<CompanyJobPositionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompanyJobPositionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompanyJobPositionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
