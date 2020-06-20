import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RouterService } from 'src/app/services/router.services';
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';
import { AuthenticationService } from 'src/app/services/authentication.services';
import { GipherService } from 'src/app/services/gipher.services';
import { NavigationEnd } from '@angular/router';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  showSearchBar: boolean;
  value: string;

  isLoginView = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService,
    private gipherService: GipherService
  ) {
    this.showSearchBar = false;
    this.authService.isUserLoggedIn.subscribe(value => {
    this.showSearchBar = value;
    });

    this.gipherService.searchGifString.subscribe(value => {
      this.value = value;
    });
  }

  ngOnInit() {
      this.router.events.pipe(
        filter((event: any) => event instanceof NavigationEnd)
      ).subscribe(event => {
        if (event.url === '/login') {
          this.isLoginView = true;
        }
      });
  }

  goToHome() {
    this.router.navigate(['/dashboard']);
  }

  logout() {
    this.authService.logout();
    this.authService.isUserLoggedIn.next(false);
    this.router.navigate(['/login']);
  }

  onEnter(value: string) {
    this.gipherService.searchGifString.next(value);
  }
}
