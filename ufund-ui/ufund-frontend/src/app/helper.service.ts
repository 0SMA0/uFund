import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { Observable, catchError, of, tap } from 'rxjs';
import { Helper } from './helper';

@Injectable({
  providedIn: 'root'
})
export class HelperService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json'}),
  };
  
  private helpersUrl = 'http://localhost:8080/helpers';
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
      if(error){

        console.error(error); // log to console instead
  
        // TODO: better job of transforming error for user consumption
        this.log(`${operation} failed: ${error.message}`);
      }

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  getHelpers(): Observable<Helper[]> {
    return this.http.get<Helper[]>(this.helpersUrl)
      .pipe(tap(
        (_) => this.log('fetched helpers'),
        catchError(this.handleError<Helper[]>('getHelpers', []))
      )
    );
  }

  getHelper(username: string): Observable<Helper> {
    const url = `${this.helpersUrl}/${username}`;
    return this.http.get<Helper>(url).pipe(
      tap(_ => this.log(`fetched helper id=${username}`)),
      catchError(this.handleError<Helper>(`getHelper id=${username}`))
    );
  }

  addHelper(username: string): Observable<Helper> {
    return this.http.post<Helper>(this.helpersUrl, username, this.httpOptions).pipe(
      tap((newHelper: Helper) => this.log(`added helper w/ name=${newHelper.username}`)),
      catchError(this.handleError<Helper>('addHelper'))
    );
  }

}
