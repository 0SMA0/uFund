import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-needs',
  templateUrl: './needs.component.html',
  styleUrls: ['./needs.component.css']
})
export class NeedsComponent implements OnInit{
  needs: Need[] = [];
  constructor(private needService: NeedService, 
              private messageService: MessageService,
              private location: Location,
              private router: Router) { }

    ngOnInit(): void {
      this.getNeeds();
    }

    getNeeds(): void {
      this.needService.getNeeds().
        subscribe(needs => this.needs = needs);
    }

    add(name: string): void {
      name = name.trim();
      if (!name) { return; }
      this.needService.addNeed({ name } as Need)
        .subscribe(need => {
          this.needs.push(need);
        });
    }
    
    press(need: Need): void {
      this.router.navigate([`/detail/${need.name}`]);
    }

    delete(need: Need): void {
      this.needs = this.needs.filter(n => n !== need);
      this.needService.deleteNeed(need.name).subscribe();
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
