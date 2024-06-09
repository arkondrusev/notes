package com.example.notes.dto.note;

import com.example.notes.model.Tag;
import lombok.Data;

import java.util.List;

@Data
public class NoteWrapper {

    private Integer noteId;
    private String noteName;
    private Integer topicId;
    private String topicName;
    private String noteContent;
    private List<Tag> noteTagList;

}
