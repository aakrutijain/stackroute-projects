export class Bookmark {
    id: string;
    username: string;
    title: string;
    url: string;
    comments: string;
    downloadsCount: string;

    constructor() {
        this.username = '';
        this.title = '';
        this.url = '';
        this.comments = '';
        this.downloadsCount = '';
      }
}
