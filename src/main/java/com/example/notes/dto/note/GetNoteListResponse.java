package com.example.notes.dto.note;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GetNoteListResponse {

    private Set<NoteWrapper> noteList;

    public Set<NoteWrapper> getNoteList() {
        if (noteList == null) {
            noteList = new HashSet<>();
        }
        return noteList;
    }
}
