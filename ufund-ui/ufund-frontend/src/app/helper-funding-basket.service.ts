import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, map, of } from 'rxjs';
import { Helper } from './helper';
import { MessageService } from './message.service';
import { FundingBasket } from './funding-basket';
import { Need } from './need';

@Injectable({
  providedIn: 'root',
})
export class HelperFundingBasketService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };

  private fundingBasketUrl = 'http://localhost:8080/baskets';
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

  getBaskets(): Observable<FundingBasket[]> {
    return this.http.get<FundingBasket[]>(this.fundingBasketUrl)
      .pipe(tap(
        (_) => this.log('fetched helpers'),
        catchError(this.handleError<FundingBasket[]>('getBaskets', []))
      )
    );
  }

  getBasket(username: string): Observable<FundingBasket> {
    const url = `${this.fundingBasketUrl}/${username}`
    
    return this.http.get<FundingBasket>(url)
      .pipe(tap(
        (_) => this.log(`fetched basket id=${username}`),
        catchError(this.handleError<FundingBasket>('getBasket id=${username}'))
      )
    );
  }


  addBasket(username: string): Observable<FundingBasket> {
    console.log(username)
    return this.http.post<FundingBasket>(this.fundingBasketUrl, username, this.httpOptions).pipe(
      tap((newBasket: FundingBasket) => this.log(`added basket w/ username=${newBasket.username}`)),
      catchError(this.handleError<FundingBasket>('addBasket'))
    );
  }

  addNeed(username: string, need: Need): Observable<FundingBasket> {
    let addNeedURL = `${this.fundingBasketUrl}/add/${username}`
    return this.http.put<FundingBasket>(addNeedURL, need, this.httpOptions).pipe(
      tap((newBasket: FundingBasket) => this.log(`added basket w/ username=${newBasket.username}`)),
      catchError(this.handleError<FundingBasket>('addBasket'))
    );
  }

  removeNeed(username: string, need: Need): Observable<FundingBasket> {
    let removeNeedURL = `${this.fundingBasketUrl}/remove/${username}`
    return this.http.put<FundingBasket>(removeNeedURL, need, this.httpOptions).pipe(
      tap((newBasket: FundingBasket) => this.log(`added basket w/ username=${newBasket.username}`)),
      catchError(this.handleError<FundingBasket>('addBasket'))
    );
  }

  // checkOut(username: string): Observable<FundingBasket> {
  //   let checkoutURL = `${this.fundingBasketUrl}/checkout`
  //   let basketObservable = this.getBasket(username)
  //   basketObservable.subscribe((fundingBasket: FundingBasket) => {
  //     for(let key in fundingBasket.basket){
  //       console.log(key + ":" + fundingBasket.basket[key])
  //     }
      
  //   })

  //   return this.http.put<FundingBasket>(checkoutURL, username, this.httpOptions).pipe(
  //     tap((newBasket: FundingBasket) => this.log(`added basket w/ username=${newBasket.username}`)),
  //     catchError(this.handleError<FundingBasket>('addBasket'))
  //   );
  // }


  


}
