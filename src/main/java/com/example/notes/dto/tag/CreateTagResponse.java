package com.example.notes.dto.tag;

import com.example.notes.dto.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder(setterPrefix = "set")
public class CreateTagResponse extends OperationResponse {

    private Integer tagId;
    private String tagName;

    public CreateTagResponse() {
        super(RESULT_CODE__OK, RESULT_MESSAGE__OK);
    }

    public CreateTagResponse(@NonNull Integer tagId, @NonNull String tagName) {
        super(RESULT_CODE__OK, RESULT_MESSAGE__OK);
        this.tagId = tagId;
        this.tagName = tagName;
    }

}
