import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBeveragesComponent } from './view-beverages.component';

describe('ViewBeveragesComponent', () => {
  let component: ViewBeveragesComponent;
  let fixture: ComponentFixture<ViewBeveragesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewBeveragesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewBeveragesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
