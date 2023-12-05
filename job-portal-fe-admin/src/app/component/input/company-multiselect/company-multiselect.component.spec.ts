import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyMultiselectComponent } from './company-multiselect.component';

describe('CompanyMultiselectComponent', () => {
  let component: CompanyMultiselectComponent;
  let fixture: ComponentFixture<CompanyMultiselectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CompanyMultiselectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CompanyMultiselectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
