package com.example.notes.dto.topic;

import lombok.Data;

@Data
public class CreateTopicRequest {

    private final String topicName;
    private final Integer parentTopicId;

}
