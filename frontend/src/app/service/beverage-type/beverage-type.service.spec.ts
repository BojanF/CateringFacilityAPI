import { TestBed, inject } from '@angular/core/testing';

import { BeverageTypeService } from './beverage-type.service';

describe('BeverageTypeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BeverageTypeService]
    });
  });

  it('should be created', inject([BeverageTypeService], (service: BeverageTypeService) => {
    expect(service).toBeTruthy();
  }));
});
