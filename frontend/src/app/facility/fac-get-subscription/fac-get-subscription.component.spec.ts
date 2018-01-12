import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacGetSubscriptionComponent } from './fac-get-subscription.component';

describe('FacGetSubscriptionComponent', () => {
  let component: FacGetSubscriptionComponent;
  let fixture: ComponentFixture<FacGetSubscriptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacGetSubscriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacGetSubscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
