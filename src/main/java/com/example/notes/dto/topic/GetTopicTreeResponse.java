package com.example.notes.dto.topic;

import com.example.notes.dto.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
public class GetTopicTreeResponse extends OperationResponse {

    private Set<TopicWrapper> topicList;

    public Set<TopicWrapper> getTopicList() {
        if (topicList == null) {
            topicList = new HashSet<>();
        }
        return topicList;
    }

    public GetTopicTreeResponse(Set<TopicWrapper> topicList) {
        super(RESULT_CODE__OK, RESULT_MESSAGE__OK);
        this.topicList = topicList;
    }

}
