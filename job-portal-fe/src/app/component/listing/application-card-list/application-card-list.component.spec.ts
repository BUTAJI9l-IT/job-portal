import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ApplicationCardListComponent} from './application-card-list.component';

describe('ApplicationCardListComponent', () => {
  let component: ApplicationCardListComponent;
  let fixture: ComponentFixture<ApplicationCardListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ApplicationCardListComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(ApplicationCardListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
