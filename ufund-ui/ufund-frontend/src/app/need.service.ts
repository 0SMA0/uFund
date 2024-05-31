import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { Need } from './need';
import { MessageService } from './message.service';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class NeedService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json'})
  };

  private needsUrl = 'http://localhost:8080/needs';
  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {}

  private log(message: string) {
    this.messageService.add('');
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  getNeeds(): Observable<Need[]> {
    return this.http.get<Need[]>(this.needsUrl)
      .pipe(tap(
          (_) => this.log('fetched needs'),
          catchError(this.handleError<Need[]>('getNeeds', []))
        )
      );
  }

  getNeed(name: string): Observable<Need> {
    const url = `${this.needsUrl}/${name}`;
    return this.http.get<Need>(url).pipe(
      tap(_ => this.log(`fetched hero id=${name}`)),
      catchError(this.handleError<Need>(`getNeed id=${name}`))
    );
  }

  updateNeed(need: Need): Observable<any> {
    return this.http.put(this.needsUrl, need, this.httpOptions).pipe(
      tap(_ => this.log(`updated need name=${need.name}`)),
      catchError(this.handleError<any>('updateNeed'))
    );
  }

  addNeed(need: Need): Observable<Need> {
    return this.http.post<Need>(this.needsUrl, need, this.httpOptions).pipe(
      tap((newNeed: Need) => this.log(`added need w/ name=${newNeed.name}`)),
      catchError(this.handleError<Need>('addNeed'))
    );
  }
  deleteNeed(name: string): Observable<Need> {
    const url = `${this.needsUrl}/${name}`;

    return this.http.delete<Need>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted need name=${name}`)),
      catchError(this.handleError<Need>('deleteNeed'))
    );
  }
  searchNeeds(term: string): Observable<Need[]>{
    if(!term.trim()) {
      return of([]);
    }
    return this.http.get<Need[]>(`${this.needsUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found needs matching "${term}"`) :
         this.log(`no needs matching "${term}"`)),
      catchError(this.handleError<Need[]>('searchNeeds', []))
    );
  }
}
