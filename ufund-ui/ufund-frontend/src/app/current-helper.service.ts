import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MessageService } from './message.service';
import { HelperService } from './helper.service';
import { Observable, catchError, of, tap } from 'rxjs';
import { Helper } from './helper';
import { getHtmlTagDefinition } from '@angular/compiler';

@Injectable({
  providedIn: 'root'
})
export class CurrentHelperService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json'}),
  };
  private currentUser = ""
  constructor(
    private http: HttpClient,
    private helperService: HelperService,
  ) {}

  setCurrent(username: string): void {
    let helperObservable = this.helperService.getHelper(username);
    helperObservable.subscribe((helper: Helper) => {
      this.currentUser = helper.username;
    })
  }

  getCurrent(): string {
    return this.currentUser;
  }
}
