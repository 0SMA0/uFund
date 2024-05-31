import { TestBed } from '@angular/core/testing';

import { CurrentHelperService } from './current-helper.service';

describe('CurrentHelperService', () => {
  let service: CurrentHelperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CurrentHelperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
