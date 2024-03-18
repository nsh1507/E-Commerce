import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { User } from '../user';
import { Userservice } from '../user.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  needs: Need[] = [];
  user: User | null = null;

  constructor(private needService: NeedService, private userService: Userservice, private router: Router) { }

  ngOnInit(): void {
    this.getNeeds();
    this.getUser();
  }

  getUser(): void {
    this.user = this.userService.getCurrentUser();
  }

  getNeeds(): void {
    this.needService.getNeeds()
      .subscribe(needs => this.needs = needs.slice(1, 5));
  }

  logOut(){
    this.userService.logoutUser();
    this.router.navigateByUrl("login");
  }

  delete() {
    if(this.user !== null) {
      this.userService.deleteUser(this.user.username).subscribe(_ => {
        this.logOut();
      });
    }
  }
}