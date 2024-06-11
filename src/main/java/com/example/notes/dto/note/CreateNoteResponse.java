package com.example.notes.dto.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class CreateNoteResponse {

    @NonNull
    private Integer noteId;
    @NonNull
    private String noteName;

}
