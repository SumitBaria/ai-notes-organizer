package com.notesorganizer.note.controller;

import com.notesorganizer.common.dtos.ApiResponse;
import com.notesorganizer.note.request.NotesSaveRequest;
import com.notesorganizer.note.response.NotesResponse;
import com.notesorganizer.note.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotesController {

    private final NotesService notesService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<NotesResponse>> saveNotes(@RequestBody NotesSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<NotesResponse>builder()
                        .data(notesService.saveNote(request))
                        .build());
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<NotesResponse>> updateNotes(@RequestBody NotesSaveRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<NotesResponse>builder()
                        .data(notesService.updateNote(request))
                        .build());
    }

    @GetMapping("/id/{noteId}")
    public ResponseEntity<ApiResponse<NotesResponse>> getNote(@PathVariable("noteId") String noteId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<NotesResponse>builder()
                        .data(notesService.getNote(noteId))
                        .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotesResponse>>> listNotes(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(ApiResponse.<List<NotesResponse>>builder()
                        .data(notesService.getNotes(userId))
                        .build());
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<ApiResponse<Object>> deleteNote(@PathVariable("noteId") String noteId) {
        try {
            notesService.deleteNote(noteId);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(ApiResponse.builder()
                            .message("Note Deleted Successfully")
                            .build());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .message(ex.getMessage())
                            .build());
        }
    }

    @PatchMapping("/{noteId}")
    public ResponseEntity<ApiResponse<Object>> pinNote(@PathVariable("noteId") String noteId, @RequestParam("pinNote") boolean pinNote) {
        try {
            notesService.pinNote(noteId, pinNote);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(ApiResponse.builder()
                            .message("Note Pinned Successfully")
                            .build());
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .message(ex.getMessage())
                            .build());
        }
    }
}
