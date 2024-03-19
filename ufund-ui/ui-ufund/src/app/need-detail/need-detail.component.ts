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
  currentUser: User | null = null;

  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private location: Location,
    public userService: Userservice
  ) {}

  ngOnInit(): void {
    this.getNeed();
    this.currentUser = this.userService.getCurrentUser();
  }

  getNeed(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.needService.getNeed(id)
      .subscribe(need => this.need = need);
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
}