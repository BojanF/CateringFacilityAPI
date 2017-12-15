import { TestBed, inject } from '@angular/core/testing';

import { ApiInvoiceService } from './api-invoice.service';

describe('ApiInvoiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApiInvoiceService]
    });
  });

  it('should be created', inject([ApiInvoiceService], (service: ApiInvoiceService) => {
    expect(service).toBeTruthy();
  }));
});
