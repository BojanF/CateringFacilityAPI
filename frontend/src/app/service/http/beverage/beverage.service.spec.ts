import { TestBed, inject } from '@angular/core/testing';

import { BeverageService } from './beverage.service';

describe('BeverageService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BeverageService]
    });
  });

  it('should be created', inject([BeverageService], (service: BeverageService) => {
    expect(service).toBeTruthy();
  }));
});
