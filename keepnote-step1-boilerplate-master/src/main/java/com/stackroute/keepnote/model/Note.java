package com.stackroute.keepnote.model;

import java.time.LocalDateTime;

public class Note {
	
	private int noteId;
	private String noteTitle;
	private String noteContent;
	private String noteStatus;
	private LocalDateTime createdAt = LocalDateTime.now();

	public Note() {
		super();
	}

	public int getNoteId() {
		return this.noteId;
	}

	public void setNoteId(int intid) {
		this.noteId = intid;
	}

	public String getNoteTitle() {
		return this.noteTitle;
	}

	public void setNoteTitle(String string) {
		this.noteTitle = string;
	}

	public String getNoteContent() {
		return this.noteContent;
	}

	public void setNoteContent(String string) {
		this.noteContent = string;
	}

	public String getNoteStatus() {
		return this.noteStatus;
	}

	public void setNoteStatus(String string) {
		this.noteStatus = string;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(LocalDateTime localdatetime) {
		this.createdAt = localdatetime;
	}

	/* Override the toString() method */

	@Override
	public String toString() {
		return null;
	}
}