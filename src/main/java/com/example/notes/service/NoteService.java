package com.example.notes.service;

import com.example.notes.dto.note.*;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    public CreateNoteResponse addNote(CreateNoteRequest request) {
        return new CreateNoteResponse();
    }

    public UpdateNoteResponse updateNote(UpdateNoteRequest request) {
        return new UpdateNoteResponse();
    }

    public DeleteNoteResponse deleteNote(DeleteNoteRequest request) {
        return new DeleteNoteResponse();
    }

    public GetNoteListResponse getNoteList() {
        return new GetNoteListResponse();
    }

}
