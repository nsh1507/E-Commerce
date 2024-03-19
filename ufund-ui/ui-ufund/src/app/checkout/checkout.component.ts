import { Component } from '@angular/core';
import { Userservice } from '../user.service';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {

  constructor(
    public userService: Userservice, 
    private location: Location, 
    private router: Router, 
    public needService: NeedService) {}

  goBack(): void {
    this.location.back();
  }

  
}
