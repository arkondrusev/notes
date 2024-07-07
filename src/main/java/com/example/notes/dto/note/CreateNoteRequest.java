package com.example.notes.dto.note;

import com.example.notes.dto.tag.TagWrapper;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
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
