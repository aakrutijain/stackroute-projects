import { Injectable } from '@angular/core';
import { Note } from '../note';
import { BehaviorSubject } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { tap } from 'rxjs/internal/operators/tap';

@Injectable()
export class NotesService {

  errMessage: string;
  notes: Array<Note> = [];
  notesSubject: BehaviorSubject<Array<Note>> = new BehaviorSubject(this.notes);

  constructor(private httpClient: HttpClient, private authenticationService: AuthenticationService) {}

  fetchNotesFromServer() {
    return this.httpClient.get<Array<Note>>('http://localhost:3000/api/v1/notes', {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authenticationService.getBearerToken()}`)
    }).subscribe((data) => {
      this.notes = data;
      this.notesSubject.next(this.notes);
    },
    error => {
      this.errMessage = error.message;
    });
  }

  getNotes(): BehaviorSubject<Array<Note>> {
    return this.notesSubject;
  }

  addNote(note: Note): Observable<Note> {
    return this.httpClient.post<Note>('http://localhost:3000/api/v1/notes', note, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authenticationService.getBearerToken()}`)
    }).pipe(tap(newnote => {
      this.notes.push(newnote);
      this.notesSubject.next(this.notes);
    }));
  }

  editNote(note: Note): Observable<Note> {
    return this.httpClient.put<Note>(`http://localhost:3000/api/v1/notes/${note.id}`, note, {
      headers: new HttpHeaders().set('Authorization', `Bearer ${this.authenticationService.getBearerToken()}`)
    }).pipe(tap(editNote => {
      const noteFin = this.notes.find(noteRes => noteRes.id === editNote.id);
      Object.assign(noteFin, editNote);
      this.notesSubject.next(this.notes);
    }));
  }

  getNoteById(noteId): Note {
    const note = this.notes.find(noteRes => noteRes.id === noteId);
    return Object.assign({}, note);
  }
}
