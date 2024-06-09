package com.example.notes.controller;

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
        return null;
    }

    @PutMapping("updateNote")
    public UpdateNoteResponse updateNote(@RequestBody UpdateNoteRequest request) {
        return null;
    }

    @DeleteMapping("deleteNote")
    public DeleteNoteResponse deleteNote(@RequestBody DeleteNoteRequest request) {
        return null;
    }

}
