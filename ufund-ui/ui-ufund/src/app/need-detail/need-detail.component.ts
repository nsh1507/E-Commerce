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

  addToCart(): void{

    // this.getUserCart();
    // if (!this.userCart){
    //   this.userCart = []
    // }

    // let cartMap: Map<number, number> = new Map();
    // let index = 0;

    // while(index < this.userCart.length){
    //   alert(this.userCart.length)
    //   if (cartMap.has(this.userCart[index].id) === false) {
    //     cartMap.set(this.userCart[index].id, 1)
    //     ++index;
    //     alert(this.userCart[index].quantity)
    //     alert(cartMap.get(this.userCart[index].id)!)
    //     if (this.userCart[index].quantity === cartMap.get(this.userCart[index].id)!){
    //       alert("No more needs to add!")
      
    //     }
    //   }
    //   else{
    //     alert("HERE 2")
    //     let updatedValue = cartMap.get(this.userCart[index].id)! + 1 
    //     cartMap.set(this.userCart[index].id, updatedValue)
    //     ++index;  
    //     if (this.userCart[index].quantity === cartMap.get(this.userCart[index].id)!){
    //       alert("No more needs to add!")
         
    //     }  
    //   }
    // }
    

    // alert("added!")
    this.userService.addToCart(this.need!)
    this.getUser()
  }

  removeFromCart(): void {    
    this.userService.removeFromCart(this.need!)
    this.getUser()
}
}