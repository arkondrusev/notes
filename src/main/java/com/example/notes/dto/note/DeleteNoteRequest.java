package com.example.notes.dto.note;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set")
public class DeleteNoteRequest {

    private Integer noteId;

}
