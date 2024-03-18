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

  constructor(private router: Router, private userService: Userservice){}

  onSignIn(name: string, password:string){
    const newAccount = {username: name, password: password}
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

  
  onLogIn(name: string, password:string){
    this.userService.loginUser(name, password).subscribe((account) => {
      if (account) {
        this.router.navigate(['/dashboard']);
      }
      else{
        alert("Account does not exist!")
      }
    });
    
  }

}

