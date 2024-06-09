package com.example.notes.dto.topic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class TopicWrapper {

    private Integer topicId;
    private String topicName;
    private Integer parentTopicId;
    private Set<TopicWrapper> childrenTopicList;

    public TopicWrapper(Integer topicId, String topicName, Integer parentTopicId) {
        this(topicId, topicName, parentTopicId, new HashSet<>());
    }

}
