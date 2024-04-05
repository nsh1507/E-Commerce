import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Feedback } from './feedback';
import { MessageService } from './message.service';


@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  private feedbacksUrl = 'http://localhost:8080/feedbacks';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET feedbacks from the server */
  getFeedbacks(): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(this.feedbacksUrl)
      .pipe(
        tap(_ => this.log('fetched feedbacks')),
        catchError(this.handleError<Feedback[]>('getFeedbacks', []))
      );
  }

  /** GET feedback by id. Return `undefined` when id not found */
  getFeedbackNo404<Data>(id: number): Observable<Feedback> {
    const url = `${this.feedbacksUrl}/?id=${id}`;
    return this.http.get<Feedback[]>(url)
      .pipe(
        map(feedbacks => feedbacks[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} feedback id=${id}`);
        }),
        catchError(this.handleError<Feedback>(`getFeedback id=${id}`))
      );
  }

  //////// Save methods //////////

  /** POST: add a new feedback to the server */
  addFeedback(feedback: Feedback): Observable<Feedback> {
    return this.http.post<Feedback>(this.feedbacksUrl, feedback, this.httpOptions).pipe(
      tap((newFeedback: Feedback) => this.log(`added feedback w/ id=${newFeedback.id}`)),
      catchError(this.handleError<Feedback>('addFeedback'))
    );
  }

  /** DELETE: delete the feedback from the server */
  deleteFeedback(id: number): Observable<Feedback> {
    const url = `${this.feedbacksUrl}/${id}`;

    return this.http.delete<Feedback>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted feedback id=${id}`)),
      catchError(this.handleError<Feedback>('deleteFeedback'))
    );
  }

  /** PUT: update the feedback on the server */
  updateFeedback(feedback: Feedback): Observable<any> {
    return this.http.put(this.feedbacksUrl, feedback, this.httpOptions).pipe(
      tap(_ => this.log(`updated feedback id=${feedback.id}`)),
      catchError(this.handleError<any>('updateFeedback'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a FeedbackService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`FeedbackService: ${message}`);
  }
}
