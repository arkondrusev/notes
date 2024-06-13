package com.example.notes.dto.topic;

import lombok.Data;

@Data
public class UpdateTopicRequest {

    private Integer topicId;
    private String topicName;
    private Integer parentTopicId;

}
