import { TestBed, inject } from '@angular/core/testing';

import { FacilityInvoiceService } from './facility-invoice.service';

describe('FacilityInvoiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FacilityInvoiceService]
    });
  });

  it('should be created', inject([FacilityInvoiceService], (service: FacilityInvoiceService) => {
    expect(service).toBeTruthy();
  }));
});
