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

    @PostMapping("create")
    public OperationResponse createNote(@RequestBody CreateNoteRequest request) {
        return noteService.createNote(request);
    }

    @GetMapping("get-list")
    public GetNoteListResponse getNoteList() {
        return noteService.getNoteList();
    }

    @PutMapping("update")
    public OperationResponse updateNote(@RequestBody UpdateNoteRequest request) {
        return noteService.updateNote(request);
    }

    @DeleteMapping("delete")
    public OperationResponse deleteNote(@RequestBody DeleteNoteRequest request) {
        return noteService.deleteNote(request);
    }

}
