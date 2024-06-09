package com.example.notes.model;

import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"parentTopic"})
public class Note {

    @EqualsAndHashCode.Include
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private Topic topic;
    private String content;
    private List<Tag> tagList;

}
