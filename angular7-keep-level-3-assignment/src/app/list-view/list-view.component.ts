import { Component, OnInit } from '@angular/core';
import { Note } from '../note';
import { NotesService } from 'src/app/services/notes.service';

@Component({
  selector: 'app-list-view',
  templateUrl: './list-view.component.html',
  styleUrls: ['./list-view.component.css']
})
export class ListViewComponent implements OnInit {
  errMessage: string;
  notes: Array<Note> = [];
  notStartedNotes: Array<Note>;
  startedNotes: Array<Note>;
  completedNotes: Array<Note>;

  constructor(private notesService: NotesService) {}

  ngOnInit() {
    this.notesService.getNotes().subscribe(
      data => {
        this.notes = data;
        this.fetchNotStartedNotes();
        this.fetchStartedNotes();
        this.fetchCompletedNotes();
      },
      error => {
        this.errMessage = error.message;
      }
    );
  }

  fetchNotStartedNotes() {
    this.notStartedNotes = this.notes.filter(note => note.state === 'not-started');
  }

  fetchStartedNotes() {
    this.startedNotes = this.notes.filter(note => note.state === 'started');
  }

  fetchCompletedNotes() {
    this.completedNotes = this.notes.filter(note => note.state === 'completed');
  }
}
