import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../user';
import { LoginStateService } from '../login-state.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})

export class LoginComponent {
    usernameInput?: string;
    success: boolean = false;

    constructor(
        private userService: UserService,
        private loginStateService: LoginStateService,
    ) {}
}

