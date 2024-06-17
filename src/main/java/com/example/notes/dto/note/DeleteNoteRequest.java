package com.example.notes.dto.note;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DeleteNoteRequest {

    private final Integer noteId;

}
