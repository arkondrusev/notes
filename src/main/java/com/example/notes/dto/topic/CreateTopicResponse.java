package com.example.notes.dto.topic;

import lombok.Data;
import lombok.NonNull;

@Data
public class CreateTopicResponse {

    private Integer topicId;
    private String topicName;
    private Integer parentTopicId;

    public CreateTopicResponse(@NonNull Integer topicId, @NonNull String topicName, Integer parentTopicId) {
        this.topicId = topicId;
        this.topicName = topicName;
        this.parentTopicId = parentTopicId;
    }

    public CreateTopicResponse(@NonNull Integer topicId, @NonNull String topicName) {
        this(topicId, topicName, null);
    }

}
