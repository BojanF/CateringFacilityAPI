import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApiInvoicesComponent } from './api-invoices.component';

describe('ApiInvoicesComponent', () => {
  let component: ApiInvoicesComponent;
  let fixture: ComponentFixture<ApiInvoicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApiInvoicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApiInvoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
