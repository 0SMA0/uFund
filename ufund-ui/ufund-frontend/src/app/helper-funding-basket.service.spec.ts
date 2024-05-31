import { TestBed } from '@angular/core/testing';

import { HelperFundingBasketService } from './helper-funding-basket.service';

describe('HelperFundingBasketService', () => {
  let service: HelperFundingBasketService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HelperFundingBasketService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
