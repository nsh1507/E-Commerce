import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackPopComponent } from './feedback-pop.component';

describe('FeedbackPopComponent', () => {
  let component: FeedbackPopComponent;
  let fixture: ComponentFixture<FeedbackPopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FeedbackPopComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FeedbackPopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
