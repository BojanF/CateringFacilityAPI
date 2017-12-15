import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacilityInvoicesComponent } from './facility-invoices.component';

describe('FacilityInvoicesComponent', () => {
  let component: FacilityInvoicesComponent;
  let fixture: ComponentFixture<FacilityInvoicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacilityInvoicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacilityInvoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
