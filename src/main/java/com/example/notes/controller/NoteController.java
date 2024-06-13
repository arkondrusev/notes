package com.example.notes.controller;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.note.*;
import com.example.notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("note")
@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("createNote")
    public CreateNoteResponse createNote(@RequestBody CreateNoteRequest request) {
        return noteService.addNote(request);
    }

    @GetMapping("getNoteList")
    public GetNoteListResponse getNoteList() {
        return noteService.getNoteList();
    }

    @PutMapping("updateNote")
    public OperationResponse updateNote(@RequestBody UpdateNoteRequest request) {
        return noteService.updateNote(request);
    }

    @DeleteMapping("deleteNote")
    public OperationResponse deleteNote(@RequestBody DeleteNoteRequest request) {
        return noteService.deleteNote(request);
    }

}
