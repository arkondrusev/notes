package com.example.notes.dto.note;

import com.example.notes.dto.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper=true)
public class CreateNoteResponse extends OperationResponse {

    @NonNull
    private Integer noteId;
    @NonNull
    private String noteName;

    public CreateNoteResponse(@NonNull Integer noteId, @NonNull String noteName) {
        super(RESULT_CODE__OK,RESULT_MESSAGE__OK);
        this.noteId = noteId;
        this.noteName = noteName;
    }

}
