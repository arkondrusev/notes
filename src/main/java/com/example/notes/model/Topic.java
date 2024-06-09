package com.example.notes.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Topic {

    @EqualsAndHashCode.Include
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    private Topic parentTopic;
    @NonNull
    private Set<Topic> children;

    public Topic(@NonNull Integer topicId, @NonNull String topicName, Topic parentTopic, @NonNull Set<Topic> children) {
        this.id = topicId;
        this.name = topicName;
        this.parentTopic = parentTopic;
        this.children = children;
    }

    public Topic(@NonNull Integer topicId, @NonNull String topicName, Topic parentTopic) {
        this(topicId, topicName, parentTopic, new HashSet<>());
    }

    public Topic(@NonNull Integer topicId, @NonNull String topicName) {
        this(topicId, topicName, null);
    }

}
