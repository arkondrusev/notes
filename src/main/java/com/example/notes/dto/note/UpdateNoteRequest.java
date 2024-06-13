package com.example.notes.dto.note;

import com.example.notes.dto.tag.TagWrapper;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class UpdateNoteRequest {

    @NonNull
    private Integer noteId;
    private String noteName;
    private Integer topicId;
    private String noteContent;
    private Set<TagWrapper> noteTagList;

}
