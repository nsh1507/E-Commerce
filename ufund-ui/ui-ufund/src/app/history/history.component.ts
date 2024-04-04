import { Component } from '@angular/core';
import { Userservice } from '../user.service';
import { Location } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { NeedService } from '../need.service';
import { Need } from '../need';
import { User } from '../user';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrl: './history.component.css'
})
export class HistoryComponent {
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
    this.userCart = this.userService.getCurrentUser()?.history;
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
  
  goBack(): void {
    this.location.back();
  }
}
