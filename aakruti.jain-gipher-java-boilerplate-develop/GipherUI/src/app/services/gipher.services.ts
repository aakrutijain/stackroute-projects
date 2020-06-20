import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, ObservableLike } from 'rxjs';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Bookmark } from 'src/app/model/bookmark';
export const USER_NAME = 'username';

@Injectable({
  providedIn: 'root'
})
export class GipherService {

  thirdPartyAPI: string;
  managerBackend: string;
  recommendationsBackend: string;
  apiKey: string;
  limit: string;
  userName: string;
  public searchGifString: BehaviorSubject<string> = new BehaviorSubject<string>('dogs');


  constructor(private httpClient: HttpClient) {
    this.thirdPartyAPI = 'https://api.giphy.com/v1/gifs/search?api_key=l9hJzMcWztLWfH9xUozhsMlQ7syZxlpr&q=';
    this.limit = '&limit=8';
    this.managerBackend = 'http://localhost:9090/giphermanager/api/v1/gifmanager/user/';
    this.recommendationsBackend = 'http://localhost:9090/gipherrecommendations/api/v1/recommendations';
   }

   getAllGifDetails(query: string): Observable<any> {
    const url = `${this.thirdPartyAPI}${query}${this.limit}`;
    console.log(url);
    return this.httpClient.get(url);
  }

  bookmark(gif: Bookmark): Observable<any> {
    this.userName = sessionStorage.getItem(USER_NAME);
    console.log(this.userName);
    const url = this.managerBackend + this.userName + '/bookmark';
    return this.httpClient.post(url, gif, {
      observe: 'response'
    });
  }

  getAllGifToBookmark(): Observable<Bookmark[]> {
    this.userName = sessionStorage.getItem(USER_NAME);
    const url = this.managerBackend + this.userName + '/bookmarks';
    return this.httpClient.get<Bookmark[]>(url);
  }

  getAllRecommendations(): Observable<Bookmark[]> {
    return this.httpClient.get<Bookmark[]>(this.recommendationsBackend);
  }

  deleteGifFromFavourite(gif: Bookmark) {
    this.userName = sessionStorage.getItem(USER_NAME);
    const url = this.managerBackend + this.userName + '/' + gif.id;
    return this.httpClient.delete(url);
  }

  updateORAddDescription(gif: Bookmark) {
    this.userName = sessionStorage.getItem(USER_NAME);
    const url = this.managerBackend + this.userName + '/' + gif.id;
    return this.httpClient.patch(url, gif, {
      observe: 'response'
    });
  }

 }
