import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DevViewActivePackagesComponent } from './dev-view-active-packages.component';

describe('DevViewActivePackagesComponent', () => {
  let component: DevViewActivePackagesComponent;
  let fixture: ComponentFixture<DevViewActivePackagesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DevViewActivePackagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DevViewActivePackagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
