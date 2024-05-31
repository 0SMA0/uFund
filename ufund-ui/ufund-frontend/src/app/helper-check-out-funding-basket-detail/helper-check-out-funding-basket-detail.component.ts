import { Component, Input } from '@angular/core';
import { Helper } from '../helper';
import { ActivatedRoute } from '@angular/router';
import { HelperFundingBasketService } from '../helper-funding-basket.service';

@Component({
  selector: 'app-helper-check-out-funding-basket-detail',
  templateUrl: './helper-check-out-funding-basket-detail.component.html',
  styleUrls: ['./helper-check-out-funding-basket-detail.component.css']
})
export class HelperCheckOutFundingBasketDetailComponent {
  @Input() fundingBasket? : Helper;
  constructor(
    private route: ActivatedRoute,
    private fundService: HelperFundingBasketService,
    private location:  Location
  ) { }
  
  // ngOnInit(): void {
  //   this.getFunds();
  // }
  // getFunds(): void {
  //   const username = String(this.route.snapshot.paramMap.get('username'));
  //   this.fundService.getFunds(username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  // }
}
