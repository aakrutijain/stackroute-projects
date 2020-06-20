import { Component } from '@angular/core';
import { Input } from '@angular/core/';
import { Note } from 'src/app/note';
import { OnInit } from '@angular/core/';
import { RouterService } from 'src/app/services/router.service';

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.css']
})

export class NoteComponent implements OnInit {
  constructor(private routerService: RouterService) {}

  @Input() note: Note;

  ngOnInit() {}

  editNote() {
    const noteId = this.note.id;
    this.routerService.routeToEditNoteView(noteId);
  }
}
