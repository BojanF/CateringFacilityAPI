import { TestBed, inject } from '@angular/core/testing';

import {PayPalService} from './paypal.service';

describe('PayPalService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PayPalService]
    });
  });

  it('should be created', inject([PayPalService], (service: PayPalService) => {
    expect(service).toBeTruthy();
  }));
});
