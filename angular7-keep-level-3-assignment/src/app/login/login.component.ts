import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { RouterService } from 'src/app/services/router.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private authenticationService: AuthenticationService, private routerService: RouterService) {}

  username = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required, Validators.minLength(6)]);

  submitMessage: string;

  loginForm = new FormGroup({
    username: this.username,
    password: this.password
  });

  loginSubmit() {
    this.authenticationService.authenticateUser(this.loginForm.value).subscribe(
      data => {
        this.authenticationService.setBearerToken(data['token']);
        this.routerService.routeToDashboard();
      },
      error => {
        if (error.message === 'Http failure response for http://localhost:3000/auth/v1/: 403 Forbidden') {
          this.submitMessage = 'Unauthorized';
        } else {
          this.submitMessage = error.message;
        }
      }
    );
  }
}
