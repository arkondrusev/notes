package com.example.notes.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"parentTopic"})
public class Topic {

    @EqualsAndHashCode.Include
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    private Topic parentTopic;

    public Topic(@NonNull Integer topicId, @NonNull String topicName, Topic parentTopic) {
        this.id = topicId;
        this.name = topicName;
        this.parentTopic = parentTopic;
    }

    public Topic(@NonNull Integer topicId, @NonNull String topicName) {
        this(topicId, topicName, null);
    }

}
