import { TestBed, inject } from '@angular/core/testing';

import { FacilityLocationContactService } from './facility-location-contact.service';

describe('FacilityLocationContactService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FacilityLocationContactService]
    });
  });

  it('should be created', inject([FacilityLocationContactService], (service: FacilityLocationContactService) => {
    expect(service).toBeTruthy();
  }));
});
