import { TestBed, inject } from '@angular/core/testing';

import { CourseTypeService } from './course-type.service';

describe('CourseTypeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CourseTypeService]
    });
  });

  it('should be created', inject([CourseTypeService], (service: CourseTypeService) => {
    expect(service).toBeTruthy();
  }));
});
