package com.example.notes.dto.topic;

import com.example.notes.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTopicResponse {

    private Integer id;
    private String Name;
    private Topic parentTopic;

}
