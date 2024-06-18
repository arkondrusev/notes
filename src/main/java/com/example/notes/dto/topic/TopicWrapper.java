package com.example.notes.dto.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicWrapper {

    private Integer topicId;
    private String topicName;
    private Integer parentTopicId;
    private Set<TopicWrapper> childrenTopicList;

    public TopicWrapper(Integer topicId, String topicName, Integer parentTopicId) {
        this(topicId, topicName, parentTopicId, new HashSet<>());
    }

    public Set<TopicWrapper> getChildrenTopicList() {
        if (childrenTopicList == null) {
            childrenTopicList = new HashSet<>();
        }
        return childrenTopicList;
    }

}
