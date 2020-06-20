export class Note {
  id: number;
  title: string;
  text: string;
  state: string;

  constructor() {
    this.title = '';
    this.text = '';
    this.state = 'not-started';
  }
}
