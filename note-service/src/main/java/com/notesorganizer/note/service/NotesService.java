package com.notesorganizer.note.service;

import com.notesorganizer.note.request.NotesSaveRequest;
import com.notesorganizer.note.response.NotesResponse;

import java.util.List;

public interface NotesService {

    NotesResponse saveNote(NotesSaveRequest notesSaveRequest);
    NotesResponse updateNote(NotesSaveRequest notesSaveRequest);
    NotesResponse  getNote(String noteId);
    List<NotesResponse> getNotes(String userId);
    void deleteNote(String noteId);
    void pinNote(String noteId, boolean pinNote);
}
