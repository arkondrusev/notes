package com.example.notes.dto.topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class UpdateTopicRequest {

    @NonNull
    private Integer topicId;
    @NonNull
    private String topicName;
    private Integer parentTopicId;

}
