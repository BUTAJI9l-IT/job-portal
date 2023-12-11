import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExtendableChipsComponent } from './extendable-chips.component';

describe('ExtendableChipsComponent', () => {
  let component: ExtendableChipsComponent;
  let fixture: ComponentFixture<ExtendableChipsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExtendableChipsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExtendableChipsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
