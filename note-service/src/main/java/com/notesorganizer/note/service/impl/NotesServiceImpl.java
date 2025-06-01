package com.notesorganizer.note.service.impl;

import com.notesorganizer.note.entity.Notes;
import com.notesorganizer.note.repository.NotesRepository;
import com.notesorganizer.note.request.NotesSaveRequest;
import com.notesorganizer.note.response.NotesResponse;
import com.notesorganizer.note.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotesServiceImpl implements NotesService {
    private final NotesRepository notesRepository;

    @Override
    public NotesResponse saveNote(NotesSaveRequest notesSaveRequest) {
        Notes notes = buildNotes(notesSaveRequest);
        notes = notesRepository.save(notes);
        return buildNotesResponse(notes);
    }

    @Override
    public NotesResponse updateNote(NotesSaveRequest notesSaveRequest) {
        Notes notes = notesRepository.findById(UUID.fromString(notesSaveRequest.getId())).orElseThrow(() -> new RuntimeException("Notes not found for Id: " + notesSaveRequest.getId()));
        notes = buildNotes(notesSaveRequest);
        notesRepository.save(notes);
        return buildNotesResponse(notes);
    }

    private NotesResponse buildNotesResponse(Notes notes) {
        NotesResponse response = new NotesResponse();
        response.setId(notes.getId().toString());
        response.setTitle(notes.getTitle());
        response.setContentBlocks(notes.getContentBlocks());
        response.setUserId(notes.getUserId().toString());
        response.setIsPinned(notes.isPinned());
        response.setIsSummarized(notes.isSummarized());
        return response;
    }

    private Notes buildNotes(NotesSaveRequest request) {
        Notes notes = new Notes();
        if(Objects.nonNull(request.getId())){
            notes.setId(UUID.fromString(request.getId()));
        }
        notes.setTitle(request.getTitle());
        notes.setContentBlocks(request.getContentBlock());
        notes.setUserId(UUID.fromString(request.getUserId()));
        notes.setPinned(request.getIsPinned());
        notes.setSummarized(request.getIsSummarized());
        return notes;
    }

    @Override
    public NotesResponse getNote(String noteId) {
        Notes notes = notesRepository.findById(UUID.fromString(noteId)).orElseThrow(() -> new RuntimeException("Notes not found for Id: " + noteId));
        return buildNotesResponse(notes);
    }

    @Override
    public List<NotesResponse> getNotes(String userId) {
        List<Notes> notesList = notesRepository.findByUserId(UUID.fromString(userId));
        return notesList.stream().map(this::buildNotesResponse).toList();
    }

    @Override
    public void deleteNote(String noteId) {
        notesRepository.deleteById(UUID.fromString(noteId));
    }

    @Override
    public void pinNote(String noteId, boolean pinNote) {
        Notes notes = notesRepository.findById(UUID.fromString(noteId)).orElseThrow(() -> new RuntimeException("Notes not found for Id: " + noteId));
        notes.setPinned(pinNote);
        notesRepository.save(notes);
    }
}
