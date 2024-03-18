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
    if (name == "" ){
      alert("Username cannot be blank")
      return;
    }
    if (password == "" ){
      alert("Password cannot be blank")
      return;
    }

    if (name == 'admin' && password == 'admin'){
      this.onLogIn(name, password); 
      return;
    }

    this.userService.addUser( {username: name, password: password} as User).subscribe((account) => {
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
    if (name == "" ){
      alert("Username cannot be blank")
      return;
    }
    if (password == "" ){
      alert("Password cannot be blank")
      return;
    }

    this.userService.loginUser(name, password).subscribe((account) => {
      if (account) {
        if (name == 'admin' && password == 'admin'){this.router.navigateByUrl("needs");}
        else{this.router.navigateByUrl("dashboard");}
      }
      else{
        alert("Account does not exist!")
      }
    });
    
  }

}

