import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';

import { Need } from './need';
import { NEEDS } from './mock-needs';
import { MessageService } from './message.service';

@Injectable({ providedIn: 'root' })
export class NeedService {

  constructor(private messageService: MessageService) { }

  getNeeds(): Observable<Need[]> {
    const needs = of(NEEDS);
    this.messageService.add('NeedService: fetched needs');
    return needs;
  }

  getNeed(id: number): Observable<Need> {
    // For now, assume that a need with the specified `id` always exists.
    // Error handling will be added in the next step of the tutorial.
    const need = NEEDS.find(h => h.id === id)!;
    this.messageService.add(`NeedService: fetched need id=${id}`);
    return of(need);
  }
}