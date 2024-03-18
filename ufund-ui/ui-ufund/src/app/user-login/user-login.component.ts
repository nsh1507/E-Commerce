import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Userservice } from '../user.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent {

  signUp: boolean = true;
  username: string = '';
  password: string = '';

  constructor(private router: Router, private userService: Userservice){}

  onSignIn(){
    const newAccount = {username: this.username, password: this.password}
    this.userService.addUser(newAccount).subscribe((account) => {
      if (account) {
        alert("Registration Successful!");
        this.signUp = false;
      }
      else{
        alert("Account already exist!")

      }
    });
  }

  
  onLogIn(){
    this.userService.loginUser(this.username, this.password).subscribe((account) => {
      if (account) {
        this.router.navigate(['/dashboard']);
      }
      else{
        alert("Account does not exist!")
      }
    });
    
  }

}

