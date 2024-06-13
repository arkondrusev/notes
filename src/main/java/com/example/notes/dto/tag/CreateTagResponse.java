package com.example.notes.dto.tag;

import com.example.notes.dto.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class CreateTagResponse extends OperationResponse {

    private Integer tagId;
    private String tagName;

    public CreateTagResponse(Integer tagId, String tagName) {
        super(RESULT_CODE__OK, RESULT_MESSAGE__OK);
        this.tagId = tagId;
        this.tagName = tagName;
    }

}
