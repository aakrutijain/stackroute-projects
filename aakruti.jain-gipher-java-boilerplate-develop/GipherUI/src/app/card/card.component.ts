import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatDialog, MatSnackBar } from '@angular/material';
import { DialogComponent } from '../dialog/dialog.component';
import { GipherService } from 'src/app/services/gipher.services';
import { Bookmark } from 'src/app/model/bookmark';


@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  @Input()
  gif: Bookmark;
  @Input()
  fData: boolean;
  @Input()
  rData: boolean;
  @Input()
  count: number;
  @Output()
  addToBookmark = new EventEmitter();

  gifs: Array<Bookmark>;
  statuscode: number;
  constructor(
    private dialog: MatDialog, private gipherService: GipherService, private matSnackbar: MatSnackBar
  ) {this.gifs = []; }

  ngOnInit() {
  }

  addButtonClick(gif) {
    this.addToBookmark.emit(gif);
  }

  deleteGifFromFavourite(gif) {
    this.gipherService.deleteGifFromFavourite(gif).subscribe(data => {
      const index = this.gifs.indexOf(gif);
      this.gifs.splice(index, 1);
      this.matSnackbar.open('Removed GIF from Bookmark', '', {
        duration: 1000
      });
      window.location.reload();
    });
    return this.gifs;
  }

}
