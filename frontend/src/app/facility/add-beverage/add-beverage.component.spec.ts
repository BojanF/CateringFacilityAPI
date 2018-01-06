import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBeverageComponent } from './add-beverage.component';

describe('AddBeverageComponent', () => {
  let component: AddBeverageComponent;
  let fixture: ComponentFixture<AddBeverageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddBeverageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBeverageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
