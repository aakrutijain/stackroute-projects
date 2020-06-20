import { Component, OnInit } from '@angular/core';
import { GipherService } from 'src/app/services/gipher.services';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Bookmark } from 'src/app/model/bookmark';

@Component({
  selector: 'app-bookmark',
  templateUrl: './bookmark.component.html',
  styleUrls: ['./bookmark.component.css']
})
export class BookmarkComponent implements OnInit {

  gifs: Array<Bookmark>;
  gif: Bookmark;
  fData = true;
  statuscode: number;
  constructor(
    private gipherService: GipherService,
    private matSnackbar: MatSnackBar) {
    this.gifs = [];
  }


  ngOnInit() {
    const message = 'Wishlist is empty !!!!';
    this.gipherService.getAllGifToBookmark().subscribe(data => {
      this.gifs = data;
      if (data.length === 0) {
        this.matSnackbar.open(message, '', {
          duration: 1000
        });
      }
    });
  }
}
