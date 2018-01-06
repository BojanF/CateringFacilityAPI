import { TestBed, inject } from '@angular/core/testing';

import { FacilityLocationService } from './facility-location.service';

describe('FacilityLocationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FacilityLocationService]
    });
  });

  it('should be created', inject([FacilityLocationService], (service: FacilityLocationService) => {
    expect(service).toBeTruthy();
  }));
});
