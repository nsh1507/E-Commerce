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
  userCart: Need[] | undefined = [];

  constructor(
    public userService: Userservice, 
    private location: Location, 
    private router: Router, 
    public needService: NeedService) {}

  ngOnInit(): void {
    this.getNeedsFromCart();
    this.getUser();
    this.getUserCart();
  }
  
  getUser(): void {
    this.user = this.userService.getCurrentUser();
  }

  getUserCart(): void{
    this.userCart = this.userService.getCurrentUser()?.cart;
  }
  
  getNeedsFromCart(): void {
    this.needService.getNeeds()
      .subscribe((needs) => {
        needs.forEach((need) => {
          let index = 0;
          while (this.userCart !== undefined && index < this.userCart.length){
            if (need.id === this.userCart[index].id) {
              this.needs.push(this.userCart[index]);
            }
            index ++;
          }
        })
      }
    );
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
