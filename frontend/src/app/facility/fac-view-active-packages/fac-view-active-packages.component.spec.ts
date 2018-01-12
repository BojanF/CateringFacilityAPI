import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FacViewActivePackagesComponent } from './fac-view-active-packages.component';

describe('FacViewActivePackagesComponent', () => {
  let component: FacViewActivePackagesComponent;
  let fixture: ComponentFixture<FacViewActivePackagesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FacViewActivePackagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FacViewActivePackagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
