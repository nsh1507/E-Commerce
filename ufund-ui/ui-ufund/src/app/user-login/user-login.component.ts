import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent {

  signUp: boolean = true;
  signUpObj: SignUpModel = new SignUpModel();
  logInObj: LoginModel = new LoginModel();

  constructor(private router: Router){}

  onSignIn(){
    const localUser = localStorage.getItem("angular17");

    if (localUser != null){
      const users = JSON.parse(localUser)
      users.push(this.signUpObj);
      localStorage.setItem("angular17", JSON.stringify(users));
    } 
    else {
      const users = [];
      users.push(this.signUpObj);
      localStorage.setItem("angular17", JSON.stringify(users));
    }
    this.signUp = false;
    alert("Registration Successful!");
  }

  
  onLogIn(){
    const localUser = localStorage.getItem("angular17");

    if (localUser != null){
      const users = JSON.parse(localUser)
      
      const isUserExist = users.find( (user:SignUpModel)=> user.username == this.logInObj.username && this.logInObj.password);
      if (isUserExist != undefined){
        alert("User Found!"); 
        localStorage.setItem('loggedUser', JSON.stringify(isUserExist));
        this.router.navigateByUrl('/dashboard');
      } 
      else {
        alert("No User Found!"); 
      }
    }
  }

}

export class SignUpModel{
  username: string;
  password: string

  constructor() {
    this.username = "";
    this.password = "";
  }
}

export class LoginModel{
  username: string;
  password: string

  constructor() {
    this.username = "";
    this.password = "";
  }
}