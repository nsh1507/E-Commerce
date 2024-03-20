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


  getUserCart(): Observable<Need[] | undefined> {
    if (this.currentUser === null) {
      return of(undefined);
    }
    const url = `${this.usersUrl}/${this.currentUser.username}/cart`;
    return this.http.get<Need[]>(url)
      .pipe(
        tap(_ => this.log('fetched user cart')),
        catchError(this.handleError<Need[]>('getUserCart', []))
      );
  }

  addToCart(need: Need): Observable<User | undefined> {
    if (this.currentUser !== null) {
      const url = `${this.usersUrl}/basket/${this.currentUser.username}`;
      return this.http.put<User>(url, need, this.httpOptions);
    }
    return of(undefined);
  }
  
  removeFromCart(need: Need): Observable<User | undefined> {
    if (this.currentUser !== null) {
      const url = `${this.usersUrl}/basket/${this.currentUser.username}/${need.id}`;
      return this.http.delete<User>(url, this.httpOptions);
    }
    return of(undefined);
  }

  // Updated handleError method to handle errors properly...
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T | undefined> => {
      console.error(error); // log to console instead
      this.log(`${operation} failed: ${error.message}`);
      return of(result);
    };
  }

  /** Log an UserService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`UserService: ${message}`);
  }
}