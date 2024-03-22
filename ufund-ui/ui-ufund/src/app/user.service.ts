import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { User } from './user';
import { Need } from './need';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class Userservice {

  private usersUrl = 'http://localhost:8080/helpers';  // URL to web api
  private currentUser: User | null = null;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET users from the server */
  getUsers(): Observable<User[] | undefined> {
    return this.http.get<User[]>(this.usersUrl)
      .pipe(
        tap(_ => this.log('fetched users')),
        catchError(this.handleError<User[]>('getUsers', []))
      );
  }

  /** GET user by username. Will 404 if username not found */
  getUser(username: string): Observable<User | undefined> {
    const url = `${this.usersUrl}/${username}`;
    return this.http.get<User>(url).pipe(
      tap(_ => this.log(`fetched user username=${username}`)),
      catchError(this.handleError<User>(`getUser username=${username}`))
    );
  }

  /** GET user by username and password. Will 401 if user not found */
  loginUser(username: string, password: string): Observable<User | undefined> {
    const url = `${this.usersUrl}/${username}?password=${password}`;
    return this.http.get<User>(url).pipe(
      tap(_ => this.log(`fetched user username=${username} password=${password}`)),
      tap(user => this.currentUser = user),
      catchError(this.handleError<User>(`getUser username=${username} password=${password}`))
    );
  }

  logoutUser(): void {
    this.currentUser = null;
  }

  //////// Save methods //////////

  /** POST: add a new user to the server */
  addUser(user: User): Observable<User | undefined> {
    return this.http.post<User>(this.usersUrl, user, this.httpOptions).pipe(
      tap((newUser: User) => this.log(`added user w/ username=${newUser.username}`)),
      tap(user => this.currentUser = user),
      catchError(this.handleError<User>('addUser'))
    );
  }

  /** DELETE: delete the user from the server */
  deleteUser(username: string): Observable<User | undefined> {
    const url = `${this.usersUrl}/${username}`;

    return this.http.delete<User>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted user username=${username}`)),
      catchError(this.handleError<User>('deleteUser'))
    );
  }

  /** PUT: update the user on the server */
  updateUser(user: User): Observable<any> {
    return this.http.put(this.usersUrl, user, this.httpOptions).pipe(
      tap(_ => this.log(`updated user username=${user.username}`)),
      catchError(this.handleError<any>('updateUser'))
    );
  }

  /** Get current logged in user NOW */
  getCurrentUser() {
    return this.currentUser;
  }

  isAdmin() {
    return (this.currentUser?.username === "admin");
  }


  addToCart(need: Need){
    if(this.currentUser !== null){
    const url = `${this.usersUrl}/basket/${this.currentUser?.username}`;
    this.http.put(url, need, this.httpOptions).pipe(
      tap(_ => this.log(`added product w/ id=${need.id} from cart`)),
      catchError(this.handleError<any>('addToShoppingCart'))
    ).subscribe(user => this.currentUser = user);
    
    return true;
    }
    return false;
  }

  removeFromCart(need: Need){
    if (this.currentUser !== null){
    const url = `${this.usersUrl}/basket/${this.currentUser?.username}/${need.id}`;

      this.http.delete(url, this.httpOptions).pipe(
        tap(_ => this.log(`deleted product w/ id=${need.id} from cart`)),
        catchError(this.handleError<any>('removeFromShoppingCart'))
      ).subscribe(user => this.currentUser = user);

      return true;
    }
    return false;
  }


  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T | undefined> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result);
    };
  }

  /** Log an UserService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`UserService: ${message}`);
  }
}