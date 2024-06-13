package com.example.notes.dto.topic;

import com.example.notes.dto.OperationResponse;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateTopicResponse extends OperationResponse {

    private Integer topicId;
    private String topicName;
    private Integer parentTopicId;

    public CreateTopicResponse(@NonNull Integer topicId, @NonNull String topicName, Integer parentTopicId) {
        super(RESULT_CODE__OK, RESULT_MESSAGE__OK);
        this.topicId = topicId;
        this.topicName = topicName;
        this.parentTopicId = parentTopicId;
    }

    public CreateTopicResponse(@NonNull Integer topicId, @NonNull String topicName) {
        this(topicId, topicName, null);
    }

}
