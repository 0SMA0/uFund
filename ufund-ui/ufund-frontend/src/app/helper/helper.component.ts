import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { CurrentHelperService } from '../current-helper.service';
import { NeedService } from '../need.service';
import { Need } from '../need';


@Component({
  selector: 'app-helper',
  templateUrl: './helper.component.html',
  styleUrls: ['./helper.component.css']
})
export class HelperComponent {
  needs: Need[] = [];
  constructor(private location: Location,
              private router: Router,
              private currentHelperService: CurrentHelperService,
              private needService: NeedService
  ) {}

  ngOnInit(): void {
    this.getNeeds();
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

  getNeeds(): void {
    this.needService.getNeeds()
      .subscribe(needs => this.needs = needs.slice(1,5));
  }

  goBack(): void {
    this.location.back();
  }

  logOut(): void {
    this.router.navigate(['/login']);
  }
}
