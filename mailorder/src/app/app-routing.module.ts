import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuardService } from './auth-guard.service';
import { DashboardComponent } from './dashboard/dashboard.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { RefillHistoryComponent } from './refill-history/refill-history.component';
import { SubscribeComponent } from './subscribe/subscribe.component';
import { SubscriptionListComponent } from './subscription-list/subscription-list.component';
import { ViewDrugsComponent } from './view-drugs/view-drugs.component';
import { ViewDuesComponent } from './view-dues/view-dues.component';
import { ViewrefillstatusComponent } from './viewrefillstatus/viewrefillstatus.component';

const routes: Routes = [
  {path:"",component:HomeComponent},
  {path:"login",component:LoginComponent},
  {path:"dashboard",component:DashboardComponent,canActivate:[AuthGuardService]},
  {path:"viewdrugs",component:ViewDrugsComponent,canActivate:[AuthGuardService]},
  {path:"subscribe/:dname/:dloc",component:SubscribeComponent,canActivate:[AuthGuardService]},
  {path:"viewrefillstatus/:sid",component:ViewrefillstatusComponent,canActivate:[AuthGuardService]},
  {path:"getsubscriptionlist",component:SubscriptionListComponent,canActivate:[AuthGuardService]},
  {path:"refillhistory",component:RefillHistoryComponent,canActivate:[AuthGuardService]},
  {path:"getdues",component:ViewDuesComponent,canActivate:[AuthGuardService]},
  {path:"logout",component:LogoutComponent,canActivate:[AuthGuardService]}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
