import { Component, OnInit } from '@angular/core';

import { Need } from '../need';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-needs',
  templateUrl: './needs.component.html',
  styleUrls: ['./needs.component.css']
})
export class NeedsComponent implements OnInit {
  needs: Need[] = [];

  constructor(private needService: NeedService) { }

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.needService.getNeeds()
    .subscribe(needs => this.needs = needs);
  }

  add(name: string, cost: number, quantity: number, type: string): void {
    name = name.trim();
    type = type.trim();
    if (!name || !type) { return; }
    if (cost <=0) {
      alert("Cost of needs have to be more than 0!")
      return;
    }
    if (quantity <=0) {
      alert("Quantity of needs have to be more than 0!")
      return;
    }
    this.needService.addNeed({name, cost, quantity, type } as Need)
      .subscribe(need => {
        this.needs.push(need);
      });
  }

  delete(need: Need): void {
    this.needs = this.needs.filter(h => h !== need);
    this.needService.deleteNeed(need.id).subscribe();
  }

}