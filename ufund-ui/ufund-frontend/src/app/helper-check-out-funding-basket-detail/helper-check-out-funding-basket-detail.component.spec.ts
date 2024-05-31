import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HelperCheckOutFundingBasketDetailComponent } from './helper-check-out-funding-basket-detail.component';

describe('HelperCheckOutFundingBasketDetailComponent', () => {
  let component: HelperCheckOutFundingBasketDetailComponent;
  let fixture: ComponentFixture<HelperCheckOutFundingBasketDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HelperCheckOutFundingBasketDetailComponent]
    });
    fixture = TestBed.createComponent(HelperCheckOutFundingBasketDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
