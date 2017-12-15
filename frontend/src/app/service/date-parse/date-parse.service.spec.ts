import { TestBed, inject } from '@angular/core/testing';

import { DateParseService } from './date-parse.service';

describe('DateParseService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DateParseService]
    });
  });

  it('should be created', inject([DateParseService], (service: DateParseService) => {
    expect(service).toBeTruthy();
  }));
});
