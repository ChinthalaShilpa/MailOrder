import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ViewrefillstatusComponent } from './viewrefillstatus/viewrefillstatus.component';
import { SubscribeComponent } from './subscribe/subscribe.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ViewDrugsComponent } from './view-drugs/view-drugs.component';
import { DashHeaderComponent } from './dash-header/dash-header.component';
import { LogoutComponent } from './logout/logout.component';
import { SubscriptionListComponent } from './subscription-list/subscription-list.component';

import { RefillHistoryComponent } from './refill-history/refill-history.component';
import { ViewDuesComponent } from './view-dues/view-dues.component';
import { NavbarComponent } from './navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    SidebarComponent,
    ViewrefillstatusComponent,
    SubscribeComponent,
    DashboardComponent,
    ViewDrugsComponent,
    DashHeaderComponent,
    LogoutComponent,
    SubscriptionListComponent,
    RefillHistoryComponent,
    ViewDuesComponent,
    NavbarComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
