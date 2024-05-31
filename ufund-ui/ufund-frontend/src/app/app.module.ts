import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NeedsComponent } from './needs/needs.component';
import { FormsModule } from '@angular/forms';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { MessagesComponent } from './messages/messages.component';
import { AppRoutingModule } from './app-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';

import { HttpClientModule } from '@angular/common/http';
import { NeedSearchComponent } from './need-search/need-search.component';
import { LoginComponent } from './login/login.component';
import { FundingBasketComponent } from './funding-basket/funding-basket.component';
import { HelperCheckOutFundingBasketDetailComponent } from './helper-check-out-funding-basket-detail/helper-check-out-funding-basket-detail.component';
import { HelperComponent } from './helper/helper.component';
import { AboutComponent } from './about/about.component';
import { HelperDetailComponent } from './helper-detail/helper-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    NeedsComponent,
    NeedDetailComponent,
    MessagesComponent,
    DashboardComponent,
    NeedSearchComponent,
    LoginComponent,
    FundingBasketComponent,
    HelperCheckOutFundingBasketDetailComponent,
    HelperComponent,
    AboutComponent,
    HelperDetailComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
