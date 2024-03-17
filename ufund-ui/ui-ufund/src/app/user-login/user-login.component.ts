import { Component } from '@angular/core';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrl: './user-login.component.css'
})
export class UserLoginComponent {

  signUp: boolean = true;
  signUpObj: SignUpModel = new SignUpModel();
  logInObj: LoginModel = new LoginModel();

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