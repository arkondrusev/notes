package com.example.notes.dto.note;

import com.example.notes.dto.tag.TagWrapper;
import lombok.*;

import java.util.Set;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set")
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
