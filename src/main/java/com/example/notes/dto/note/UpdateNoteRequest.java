package com.example.notes.dto.note;

import com.example.notes.dto.tag.TagWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class UpdateNoteRequest {

    private Integer noteId;
    private String noteName;
    private Integer topicId;
    private String noteContent;
    private Set<TagWrapper> noteTagList;

    public UpdateNoteRequest(Integer noteId, String noteName, Integer topicId) {
        this.noteId = noteId;
        this.noteName = noteName;
        this.topicId = topicId;
    }

    public Set<TagWrapper> getNoteTagList() {
        if (noteTagList == null) {
            noteTagList = new HashSet<>();
        }
        return noteTagList;
    }

}
