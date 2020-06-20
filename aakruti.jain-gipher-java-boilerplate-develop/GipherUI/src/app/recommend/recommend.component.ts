import { Component, OnInit } from '@angular/core';
import { GipherService } from 'src/app/services/gipher.services';
import { MatSnackBar } from '@angular/material';
import { Bookmark } from 'src/app/model/bookmark';

@Component({
  selector: 'app-recommend',
  templateUrl: './recommend.component.html',
  styleUrls: ['./recommend.component.css']
})
export class RecommendComponent implements OnInit {

  gifs: Array<Bookmark>;
  rData = true;
  count = 1;
  statuscode: number;
  constructor(
    private gipherService: GipherService,
    private matSnackbar: MatSnackBar
  ) {
    this.gifs = [];
   }

  ngOnInit() {
    const message = 'No recommendations !!!!';
    this.gipherService.getAllRecommendations().subscribe(data => {
      this.gifs = data;
      if (data.length === 0) {
        this.matSnackbar.open(message, '', {
          duration: 1000
        });
      }
    });
  }

}
