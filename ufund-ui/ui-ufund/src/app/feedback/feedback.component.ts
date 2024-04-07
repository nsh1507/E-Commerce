import { Component } from '@angular/core';
import { Feedback } from '../feedback';
import { FeedbackService } from '../feedback.service';
import { Userservice } from '../user.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.component.html',
  styleUrl: './feedback.component.css'
})
export class FeedbackComponent {

  feedbacks: Feedback[] = [];
  addPop = false;

  constructor(private feedbackService: FeedbackService, public userService: Userservice, private router: Router, private location: Location) { }

  ngOnInit(): void {
    this.getFeedbacks();
  }

  getFeedbacks(): void {
    this.feedbackService.getFeedbacks()
    .subscribe(feedbacks => this.feedbacks = feedbacks);
  }

  add(feedback: string): void {
    feedback = feedback.trim()
    if (!feedback) { return; }
    this.addPop = true;
    setTimeout(() => {
      this.addPop = false;
    }, 950);
    this.feedbackService.addFeedback({ feedback } as Feedback)
      .subscribe(feedback => {
        this.feedbacks.push(feedback);
      });
  }

  delete(feedback: Feedback): void {
    this.feedbacks = this.feedbacks.filter(h => h !== feedback);
    this.feedbackService.deleteFeedback(feedback.id).subscribe();
  }

  logOut(){
    this.userService.logoutUser();
    this.router.navigateByUrl("login");
  }

  goBack(): void {
    this.location.back();
  }

}
