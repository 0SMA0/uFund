import { Component, OnInit } from '@angular/core';
import { Helper } from '../helper';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { KeyValue, Location } from '@angular/common';
import { CurrentHelperService } from '../current-helper.service';
import { Router } from '@angular/router';
import { HelperFundingBasketService } from '../helper-funding-basket.service';
import { FundingBasket } from '../funding-basket';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['./funding-basket.component.css']
})
export class FundingBasketComponent implements OnInit{
  fundingBasket: {} = {};
  needs: Need[] = [];
  currentUser: String = "";
  totalFund: number = 0;

  constructor(
    private needService: NeedService,
    private currentHelperService: CurrentHelperService,
    private fundingBasketService: HelperFundingBasketService,
    private location: Location,
    private router: Router) { }
  
    keys(obj: any): string[] {
      return Object.keys(obj);
    }

    ngOnInit(): void {
      this.getNeeds();
      this.getFunds(this.currentHelperService.getCurrent());
      this.currentUser = this.currentHelperService.getCurrent();
      let fundDisplay = document.getElementById("fundDisplay") 
      if(fundDisplay){
        fundDisplay.textContent = `Funds for this Session: ${this.totalFund}`     
      }
      console.log(this.totalFund);
    }

    getNeeds(): void {
      this.needService.getNeeds().
        subscribe(needs => this.needs = needs);
    }

    getFunds(username: string): void {      
      this.fundingBasketService.getBasket(username.toString()).
        subscribe(fundingBasket => this.fundingBasket = fundingBasket.basket);
    }
    
    addNeed(need: Need){
      console.log(this.currentUser)

      this.fundingBasketService.addNeed(this.currentUser.toString(), need)
      .subscribe(
        (newBasket: FundingBasket) => {
          console.log(`added basket w/ username=${newBasket.username}`);
          
          // Get the updated basket after adding the need
          this.fundingBasketService.getBasket(this.currentUser.toString())
            .subscribe(
              (fundingBasket: FundingBasket) => {
                let basket = fundingBasket.basket;
                console.log(basket);
                this.ngOnInit();
              },
              error => {
                console.error('Error getting basket after adding need:', error);
              }
            );
        },
        error => {
          console.error('Error adding need:', error);
        }
      );
    }

    removeNeed(needKV: KeyValue<string, Need>){
      console.log(needKV.value)
      console.log(this.currentUser)

      this.fundingBasketService.removeNeed(this.currentUser.toString(), needKV.value)
      .subscribe(
        (newBasket: FundingBasket) => {
          console.log(`added basket w/ username=${newBasket.username}`);
          
          // Get the updated basket after adding the need
          this.fundingBasketService.getBasket(this.currentUser.toString())
            .subscribe(
              (fundingBasket: FundingBasket) => {
                let basket = fundingBasket.basket;
                console.log(basket);
                this.ngOnInit();
              },
              error => {
                console.error('Error getting basket after adding need:', error);
              }
            );
        },
        error => {
          console.error('Error adding need:', error);
        }
    )}

    checkOut(): void {
      for(let needName in this.fundingBasket){
        console.log(needName)
        this.needService.getNeed(needName).subscribe((value: Need) => {
          let need = value;
          this.totalFund = this.totalFund + ((need.cost.valueOf() * need.quantity.valueOf()))
          if (need){
            console.log("tried to remove")
            this.fundingBasketService.removeNeed(this.currentUser.toString(), need)
            .subscribe(
              (newBasket: FundingBasket) => {
                console.log(`added basket w/ username=${newBasket.username}`);
                
                // Get the updated basket after adding the need
                this.fundingBasketService.getBasket(this.currentUser.toString())
                  .subscribe(
                    (fundingBasket: FundingBasket) => {
                      let basket = fundingBasket.basket;
                      console.log(basket);
                      this.ngOnInit();
                    },
                    error => {
                      console.error('Error getting basket after adding need:', error);
                    }
                  );
              },
              error => {
                console.error('Error adding need:', error);
              }
          )          }
        });
      }
    }

    goDetail(needName: string): void {
      this.router.navigate([`/helperdetail/${needName}`]);
    }

    goDash(): void {
    let username = this.currentHelperService.getCurrent();
    console.log(username)
    this.router.navigate([`/dashboard/${username}`]);
    }

    goFund(): void {
    let username = this.currentHelperService.getCurrent();
    this.router.navigate([`/funding-basket/${username}`]);
    }

    goBack(): void {
    this.location.back();
    }

    logOut(): void {
    this.router.navigate(['/login']);
    }
  }
    
//     @Component({
//       selector: 'app-needs',
//       templateUrl: './needs.component.html',
//       styleUrls: ['./needs.component.css']
//     })
//     export class NeedsComponent implements OnInit{
//       // needs = NEEDS;
//       // selectedNeed?: Need;
//       needs: Need[] = [];
//       constructor(private needService: NeedService, private messageService: MessageService) { }
    
//         ngOnInit(): void {
//           this.getNeeds();
//         }
//         // onSelect(need: Need): void {
//         //   this.selectedNeed = need;
//         //   this.messageService.add('NeedsComponent: Selected need = id=${need.name}')
//         // }
//         getNeeds(): void {
//           this.needService.getNeeds().
//             subscribe(needs => this.needs = needs);
//         }


//     getFunds(username: string): void {
//       this.fundService.getFunds(username).
//         subscribe(fundingBasket => fundingBasket);
//     }
// }
