import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent {
  constructor(
    private location: Location
  ){}

  goBack(): void {
    this.location.back();
  }
}
