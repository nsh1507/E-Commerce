import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Userservice } from '../user.service';
import { User } from '../user'; 

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent {

  signUp: boolean = true;

  constructor(private router: Router, private userService: Userservice){}

  onSignIn(name: string, password:string){
    if (name.trim() == "" ){
      alert("Username cannot be blank")
      return;
    }
    if (password.trim() == "" ){
      alert("Password cannot be blank")
      return;
    }

    if (name.trim() == 'admin' && password.trim() == 'admin'){
      this.onLogIn(name.trim(), password.trim()); 
      return;
    }

    this.userService.addUser( {username: name.trim(), password: password.trim()} as User).subscribe((account) => {
      if (account) {
        alert("Registration Successful!");
        this.signUp = false;
      }
      else{
        alert("Username is Taken!")
      }
    });
  }

  
  onLogIn(name: string, password:string){
    if (name.trim() == "" ){
      alert("Username cannot be blank")
      return;
    }
    if (password.trim() == "" ){
      alert("Password cannot be blank")
      return;
    }

    this.userService.loginUser(name.trim(), password.trim()).subscribe((account) => {
      if (account) {
        if (name.trim() == 'admin' && password.trim() == 'admin'){this.router.navigateByUrl("needs");}
        else{this.router.navigateByUrl("dashboard");}
      }
      else{
        alert("Account does not exist!")
      }
    });
    
  }
}

