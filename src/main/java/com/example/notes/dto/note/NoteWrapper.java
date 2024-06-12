package com.example.notes.dto.note;

import com.example.notes.dto.tag.TagWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NoteWrapper {

    @NonNull
    private Integer noteId;
    @NonNull
    private String noteName;
    @NonNull
    private Integer topicId;
    private String topicName;
    private String noteContent;
    private Set<TagWrapper> noteTagList;

}
