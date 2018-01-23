import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PackagesStatsComponent } from './packages-stats.component';

describe('PackagesStatsComponent', () => {
  let component: PackagesStatsComponent;
  let fixture: ComponentFixture<PackagesStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PackagesStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PackagesStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
