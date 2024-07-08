package com.example.notes.dto.note;

import com.example.notes.dto.tag.TagWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNoteRequest {

    private String noteName;
    private Integer topicId;
    private String noteContent;
    private Set<TagWrapper> noteTagList;

    public Set<TagWrapper> getNoteTagList() {
        if (noteTagList == null) {
            noteTagList = new HashSet<>();
        }
        return noteTagList;
    }

}
