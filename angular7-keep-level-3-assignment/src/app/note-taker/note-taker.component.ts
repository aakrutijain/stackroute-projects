import { Component, OnInit } from '@angular/core';
import { Note } from 'src/app/note';
import { NotesService } from 'src/app/services/notes.service';

@Component({
  selector: 'app-note-taker',
  templateUrl: './note-taker.component.html',
  styleUrls: ['./note-taker.component.css']
})
export class NoteTakerComponent {

  errMessage: string;
  note: Note = new Note();
  notes: Array<Note> = [];
  states: Array<string> = ['not-started', 'started', 'completed'];

  constructor(private notesService: NotesService) {}

  takeNote() {
    if (this.note.title === '' || this.note.text === '') {
      this.errMessage = 'Title and Text both are required fields';
    }
    this.notesService.addNote(this.note).subscribe(
      data => {
        this.notes.push(data);
      },
      error => {
        this.errMessage = error.message;
      }
    );
    this.note = new Note();
  }
}
