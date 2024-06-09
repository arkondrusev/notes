package com.example.notes.dto.topic;

import lombok.Data;

@Data
public class CreateTopicRequest {

    private final String name;
    private final Integer parentTopicId;

}
