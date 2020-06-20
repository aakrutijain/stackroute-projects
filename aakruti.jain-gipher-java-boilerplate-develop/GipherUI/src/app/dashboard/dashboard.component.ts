import { Component, OnInit } from '@angular/core';
import { GipherService } from 'src/app/services/gipher.services';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material';
import { Bookmark } from 'src/app/model/bookmark';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  favouriteObj: Bookmark;
  gifs: Array<Bookmark>;
  gifSearchText: string;
  statusCode: number;
  errorStatus: string;

  value: string;

  constructor(
    private gipherService: GipherService,
    private routes: ActivatedRoute,
    private matSnackBar: MatSnackBar
  ) {
    this.gifs = [];
  }

  ngOnInit() {
    this.gipherService.searchGifString.subscribe(value => {
      this.value = value;
      this.searchGifs(this.value);
    });
  }

  searchGifs(value: string) {
    this.gipherService.getAllGifDetails(this.value).subscribe(gifData => {
      const data = gifData['data'];
      this.gifs = [];
      data.forEach(element => {
        this.favouriteObj = new Bookmark();
        this.favouriteObj.id = element['id'];
        this.favouriteObj.title = element['title'];
        if (element['username'] === '' || element['username'] == null) {
          this.favouriteObj.username = 'Anonymous';
        } else {
          this.favouriteObj.username = element['username'];
        }
        this.favouriteObj.url = element['images']['480w_still']['url'];

        this.gifs.push(this.favouriteObj);
      });
    });
  }

  bookmark(gif) {
    console.log('inside container ' + gif);
    this.gipherService.bookmark(gif).subscribe(data => {
      this.statusCode = data.status;
      if (this.statusCode === 201) {
        this.matSnackBar.open('Bookmarked the GIF', '', {
          duration: 1000
        });
      }
    },
      error => {
        this.errorStatus = `${error.status}`;
        this.statusCode = parseInt(this.errorStatus, 10);
        if (this.statusCode === 409 || this.statusCode === 500) {
          this.matSnackBar.open('GIF already bookmarked', '', {
            duration: 1000
          });
          this.statusCode = 0;
        }
      });
  }

}
