package com.example.notes.dto.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class UpdateTopicRequest {

    private Integer topicId;
    @NonNull
    private String topicName;
    private Integer parentTopicId;

}
