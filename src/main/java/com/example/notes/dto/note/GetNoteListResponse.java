package com.example.notes.dto.note;

import lombok.Data;

import java.util.Set;

@Data
public class GetNoteListResponse {

    private Set<NoteWrapper> noteList;

}
