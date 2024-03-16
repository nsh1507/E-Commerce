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
<<<<<<< HEAD
=======
import { RouterModule } from '@angular/router';
>>>>>>> main

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
<<<<<<< HEAD
=======
    HttpClientModule,
    RouterModule,

    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
   
>>>>>>> main
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    NeedsComponent,
    NeedDetailComponent,
    MessagesComponent,
    NeedSearchComponent,
    UserLoginComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }