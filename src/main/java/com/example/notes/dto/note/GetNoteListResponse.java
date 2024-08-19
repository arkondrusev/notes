package com.example.notes.dto.note;

import com.example.notes.dto.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder(setterPrefix = "set")
public class GetNoteListResponse  extends OperationResponse {

    private Set<NoteWrapper> noteList;

    public Set<NoteWrapper> getNoteList() {
        if (noteList == null) {
            noteList = new HashSet<>();
        }
        return noteList;
    }

    public GetNoteListResponse(Set<NoteWrapper> noteList) {
        super(RESULT_CODE__OK, RESULT_MESSAGE__OK);
        this.noteList = noteList;
    }

}
