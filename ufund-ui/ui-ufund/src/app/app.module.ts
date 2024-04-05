import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { NeedsComponent } from './needs/needs.component';
import { NeedSearchComponent } from './need-search/need-search.component';
import { MessagesComponent } from './messages/messages.component';
import { UserLoginComponent } from './user-login/user-login.component';
import { RouterModule } from '@angular/router';
import { CheckoutComponent } from './checkout/checkout.component';
import { HistoryComponent } from './history/history.component';
import { FeedbackComponent } from './feedback/feedback.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,

    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
   
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    NeedsComponent,
    NeedDetailComponent,
    MessagesComponent,
    NeedSearchComponent,
    UserLoginComponent,
    CheckoutComponent,
    HistoryComponent,
    FeedbackComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }