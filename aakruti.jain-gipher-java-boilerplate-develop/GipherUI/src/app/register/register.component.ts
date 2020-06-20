import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthenticationService } from 'src/app/services/authentication.services';
import { User } from 'src/app/model/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: User;
  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private snackbar: MatSnackBar
  ) {
    this.user = new User();
   }

  register() {
    this.authService.registerUser(this.user).subscribe(data => {
      if (data.status === 201) {
          this.snackbar.open('successfully registered', '', {
            duration: 1000
          });
      }
      this.router.navigate(['/login']);
    }, error => {
      if (error.status === 409) {
        const errorMsg = error.error.errorMsg;
        this.snackbar.open(errorMsg, '', {
          duration: 1000
        });
      }
   });
  }

  ngOnInit() {
  }

}
