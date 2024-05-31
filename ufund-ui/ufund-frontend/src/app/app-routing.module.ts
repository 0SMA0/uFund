import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NeedsComponent } from './needs/needs.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { LoginComponent } from './login/login.component';
import { FundingBasketComponent } from './funding-basket/funding-basket.component';
import { HelperComponent } from './helper/helper.component';
import { AboutComponent } from './about/about.component';
import { HelperDetailComponent } from './helper-detail/helper-detail.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent},
  { path: 'dashboard/admin', component: DashboardComponent },
  { path: 'dashboard/:helper', component: HelperComponent},
  { path: 'funding-basket/:name', component: FundingBasketComponent},
  { path: 'detail/:name', component: NeedDetailComponent},
  { path: 'helperdetail/:name', component: HelperDetailComponent},
  { path: 'needs', component: NeedsComponent},
  { path: 'about', component: AboutComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
