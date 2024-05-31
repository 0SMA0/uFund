import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginComponent } from './login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'The Sam & Friends Project';

  constructor(private router: Router ) {}

  goAbout(){
    this.router.navigate(['/about']);
  }
}
