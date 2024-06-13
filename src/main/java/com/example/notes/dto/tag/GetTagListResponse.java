package com.example.notes.dto.tag;

import com.example.notes.dto.OperationResponse;
import com.example.notes.model.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
public class GetTagListResponse extends OperationResponse {

    private Set<Tag> tagList;

    public GetTagListResponse(Set<Tag> tagList) {
        super(RESULT_CODE__OK, RESULT_MESSAGE__OK);
        this.tagList = tagList;
    }

    public Set<Tag> getTagList() {
        if (tagList == null) {
            tagList = new HashSet<>();
        }
        return tagList;
    }

}
