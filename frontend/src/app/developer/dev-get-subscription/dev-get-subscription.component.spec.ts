import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DevGetSubscriptionComponent } from './dev-get-subscription.component';

describe('DevGetSubscriptionComponent', () => {
  let component: DevGetSubscriptionComponent;
  let fixture: ComponentFixture<DevGetSubscriptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DevGetSubscriptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DevGetSubscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
