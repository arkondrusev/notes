package com.example.notes.dto.topic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class CreateTopicRequest {

    private final String topicName;
    private final Integer parentTopicId;

}
