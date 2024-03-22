import { Component } from '@angular/core';
import { Userservice } from '../user.service';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
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
  need: Need | undefined;
  user: User | null = null;
  userCart: Need[] | undefined = [];

  constructor(
    private route: ActivatedRoute,
    public userService: Userservice, 
    private location: Location, 
    private router: Router, 
    public needService: NeedService) {}

  ngOnInit(): void {
    this.getNeedsFromCart();
    this.getUser();
    this.getUserCart();;
  }
  
  getUser(): void {
    this.user = this.userService.getCurrentUser();
  }

  getUserCart(): void{
    this.userCart = this.userService.getCurrentUser()?.cart;
  }
  

  getNeed(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.needService.getNeed(id)
      .subscribe(need => this.need = need);
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

  checkout() {
    this.getUserCart();
    if (!this.userCart){
      this.userCart = []
    }
    


    let cartMap: Map<number, number> = new Map();
    let index = 0;

    if(this.userCart.length !== 0 && index < this.userCart.length){
      while(index < this.userCart.length){
        if (cartMap.has(this.userCart[index].id) === false) {
          cartMap.set(this.userCart[index].id, 1)
          ++index;
        }
        else{
          let updatedValue = cartMap.get(this.userCart[index].id)! + 1 
          cartMap.set(this.userCart[index].id, updatedValue)
          ++index;
        }
      }
    }


    for (let product of this.userCart) {
      product.quantity = product.quantity - cartMap.get(product.id)!;
      if (product.quantity < 0) {product.quantity = 0;}
      this.userService.removeFromCart(product);
      this.needService.updateNeed(product)
      .subscribe(() => this.router.navigateByUrl("dashboard"));
    }
  }
}
