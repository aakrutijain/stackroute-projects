import { Component } from '@angular/core';
import { RouterService } from 'src/app/services/router.service';
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  isNoteView = true;

  constructor(private routerService: RouterService) {}

  changeView() {
    if (this.isNoteView) {
      this.routerService.routeToListView();
      this.isNoteView = false;
    } else {
      this.routerService.routeToNoteView();
      this.isNoteView = true;
    }
  }

}
