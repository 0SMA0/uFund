import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Helper } from '../helper';
import { HelperService } from '../helper.service';
import { CurrentHelperService } from '../current-helper.service';
import { HelperFundingBasketService } from '../helper-funding-basket.service';
import { FundingBasket } from '../funding-basket';
import { Need } from '../need';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loggedInUser: string | null = null;
  helpers: Helper[] = [];
  baskets: FundingBasket[] = [];
  constructor(private router: Router, 
              private helperService: HelperService,
              private fundingBasketService: HelperFundingBasketService,
              private currentHelperService: CurrentHelperService
    ) {}
    
    ngOnInit(): void {
      this.getHelpers();
    }

    getHelpers(): void {
      this.helperService.getHelpers().
        subscribe(helpers => this.helpers = helpers);
    }

    goDash(givenUsername: string): void {
      if (!givenUsername) {
        let message = document.getElementById("message")
        if(message){
          message.textContent = 'Username is empty. Please provide a username.';
        }        
        return;
      }
      if(givenUsername === "admin"){
        this.loggedInUser = givenUsername;
        this.router.navigate(['/dashboard/admin']);
      }
      
      let found = false;
      let helpersObservable = this.helperService.getHelpers();
      helpersObservable.subscribe((helpers: Helper[]) => {
        helpers.forEach(helper => {
          if(helper.username.toString() === givenUsername){
            found = true;
            this.currentHelperService.setCurrent(helper.username)
            this.router.navigate([`/dashboard/${givenUsername}`]);
          }
        })
      })
      let message = document.getElementById("message")
      if(!found && message){
        message.textContent = 'Username entered does not exist.';
      }      
    };

    create(username: string): void {
      username = username.trim();
      if (!username) {
        let message = document.getElementById("message")
        if(message){
          message.textContent = 'Username is empty.    Please provide a username.';
        }     
        return;
      }


      this.fundingBasketService.addBasket(username).subscribe(basket => {this.baskets.push(basket)})



      if(username === "admin") {
        let message = document.getElementById("message")
        if(message){
          message.textContent = "Cannot use admin as a username"
        }
        return;
      }
      else {
        let found = false;
        let helpersObservable = this.helperService.getHelpers();
        helpersObservable.subscribe((helpers: Helper[]) => {
          helpers.forEach(helper => {
            if(helper.username.toString() === username){
              found = true;
              let message = document.getElementById("message")
              if(message && found){
                message.textContent = `${username} already taken.`
              }
              return;
            }
          })
        })
        let message = document.getElementById("message")
        if(message){
          message.textContent = `${username} created!`
        }
        this.helperService.addHelper(username).subscribe(helper => {this.helpers.push(helper)})
        this.fundingBasketService.addBasket(username).subscribe(basket => {this.baskets.push(basket)})

        let basketObservable = this.fundingBasketService.getBasket(username);
        console.log(basketObservable)
        basketObservable.subscribe((fundingBasket: FundingBasket) => {
          console.log(fundingBasket.basket)
        })
      }
    };
  }

