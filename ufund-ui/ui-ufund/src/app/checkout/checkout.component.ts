import { Component } from '@angular/core';
import { Userservice } from '../user.service';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { NeedService } from '../need.service';
import { Need } from '../need';
import { User } from '../user';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {
  needs: Need[] = [];
  user: User | null = null;

  constructor(
    public userService: Userservice, 
    private location: Location, 
    private router: Router, 
    public needService: NeedService) {}

  ngOnInit(): void {
    this.getNeeds();
    this.getUser();
  }
  
  getUser(): void {
    this.user = this.userService.getCurrentUser();
  }
  
  getNeeds(): void {
    let index = 0;
    this.needService.getNeeds()
      .subscribe((needs) => {
        needs.forEach((need) => {
        if (need.id === this.userService.getCurrentUser()?.cart[index++].id) {
          this.needs.push(need);
        }
      })});
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

  goBack(): void {
    this.location.back();
  }

  
}
