import { Component, Input, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

import { Need } from '../need';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-need-search',
  templateUrl: './need-search.component.html',
  styleUrls: ['./need-search.component.css']
})
export class NeedSearchComponent implements OnInit{
  @Input() searchRouterLink: string = '/detail';
  needs$!: Observable<Need[]>;
  private searchTerms = new Subject<string>();

  constructor(private needService: NeedService) {}

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit(): void {
    this.needs$ = this.searchTerms.pipe
      (debounceTime(300), distinctUntilChanged(), 
      switchMap((term: string) => this.needService.searchNeeds(term))
      );
  }
}
