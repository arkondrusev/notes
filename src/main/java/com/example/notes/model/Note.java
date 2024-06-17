package com.example.notes.model;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"topic"})
public class Note {

    @EqualsAndHashCode.Include
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    private Topic topic;
    private String content;
    private Set<Tag> tagList;

    public Note(Integer id, String name, Topic parentTopic, String content) {
        this(id, name, parentTopic, content, new HashSet<>());
    }

    public Note(Integer id, String name, Topic parentTopic) {
        this(id, name, parentTopic, "");
    }

    public Set<Tag> getTagList() {
        if (tagList == null) {
            tagList = new HashSet<>();
        }
        return tagList;
    }

}
