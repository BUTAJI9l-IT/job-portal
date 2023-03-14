import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CompanyCardListComponent} from './company-card-list.component';

describe('CompanyCardListComponent', () => {
  let component: CompanyCardListComponent;
  let fixture: ComponentFixture<CompanyCardListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CompanyCardListComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CompanyCardListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
