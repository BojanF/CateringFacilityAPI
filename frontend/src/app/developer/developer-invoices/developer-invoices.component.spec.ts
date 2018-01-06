import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeveloperInvoicesComponent } from './developer-invoices.component';

describe('DeveloperInvoicesComponent', () => {
  let component: DeveloperInvoicesComponent;
  let fixture: ComponentFixture<DeveloperInvoicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeveloperInvoicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeveloperInvoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
