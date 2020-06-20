import { Component, OnInit } from '@angular/core';

import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.services';
import { User } from 'src/app/model/user';

export const TOKEN_NAME = 'jwt_token';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User;
  errMessage: string;

  constructor(
    private authService: AuthenticationService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {
    this.user = new User();
  }

  ngOnInit() {
  }

  login() {
    this.authService.loginUser(this.user).subscribe(data => {
      if (data) {
        localStorage.setItem(TOKEN_NAME, data.body['token']);
        this.snackBar.open(data.body['message'], '', {
          duration: 1000
        });
        this.authService.isUserLoggedIn.next(true);
        this.router.navigate(['/dashboard']);
      }
    },
    error => {
      if (error.status === 401) {
        this.errMessage = error.error.message;
      }
    });
  }

}
