import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Need } from '../need';
import { NeedService } from '../need.service';
import { Userservice } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: [ './need-detail.component.css' ]
})
export class NeedDetailComponent implements OnInit {
  need: Need | undefined;
  needs: Need[] = [];
  currentUser: User | null = null;
  userCart: Need[] | undefined = [];
  totalCost: number = 0;
  totalQuantity: number = 0;

  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private location: Location,
    public userService: Userservice
  ) {}

  ngOnInit(): void {
    this.getNeed();
    this.getUserCart();
    this.getNeedsFromCart()
    this.currentUser = this.userService.getCurrentUser();
  }

  getNeed(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.needService.getNeed(id)
      .subscribe(need => this.need = need);
  }


  getUser(): void {
    this.currentUser = this.userService.getCurrentUser();
  }

  getUserCart(): void{
    this.userCart = this.userService.getCurrentUser()?.cart;
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.need) {
      if (this.need.cost < 0){
        alert("Cost of needs have to be more than 0!")
        return;
      }
      if (this.need.quantity < 0){
        alert("Quantity of needs have to be more than 0!")
        return;
      }
      this.needService.updateNeed(this.need)
        .subscribe(() => this.goBack());
    }
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

  refreshCartDisplay(): void {
    this.userService.getUserCart().subscribe(
      (cart: Need[] | undefined) => {
        this.userCart = cart;
        if (cart) {
          // Proceed with operations that require cart to be defined
          this.updateCartTotals();
        } else {
          // Handle the case where cart is undefined
          // For example, you might clear the totals or display a message
          console.log("Cart is undefined.");
        }
      },
      error => {
        console.error('Error refreshing cart:', error);
        alert('There was an error refreshing your cart.');
      }
    );
}

updateCartTotals(): void {
  this.totalCost = 0;
  this.totalQuantity = 0;

  this.userCart?.forEach(need => {
    this.totalCost += need.cost * need.quantity;
    this.totalQuantity += need.quantity;
  });
}

addToCart() {
  if (!this.need) {
    alert("No need selected to add to cart.");
    return;
  }
  // Assuming a method to get count of this need in the cart exists
  let currentNeedQuantity = this.countNeed(this.need);
  
  if (currentNeedQuantity < this.need.quantity) {
    this.userService.addToCart(this.need).subscribe(() => {
      this.refreshCartDisplay(); // Make sure this method effectively updates the UI
    });
  } else {
    alert("Cannot add more of this item as it exceeds the available quantity.");
  }
}

removeFromCart() {
  if (!this.need) {
    alert("No need selected to remove from cart.");
    return;
  }
  this.userService.removeFromCart(this.need).subscribe(() => {
    this.refreshCartDisplay(); // Ensure this method updates the UI as expected
  });
}

  countNeed(need: Need): number {
    return this.userCart?.filter(item => item.id === need.id).length || 0;
  }
}