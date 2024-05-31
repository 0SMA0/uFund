import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  needs: Need[] = [];

  constructor(private needService: NeedService, 
              private location: Location,
              private router: Router
  ){}
  
  ngOnInit(): void {
    this.getNeeds();
  }
  getNeeds(): void {
    this.needService.getNeeds()
      .subscribe(needs => this.needs = needs.slice(1,5));
  }
  goDash(): void {
  this.router.navigate([`/dashboard/admin`]);
  }

  goCupboard(): void {
  this.router.navigate([`/needs`]);
  }

  goBack(): void {
  this.location.back();
  }

  logOut(): void {
  this.router.navigate(['/login']);
  }

}
