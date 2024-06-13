package com.example.notes.dto.tag;

import com.example.notes.dto.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
public class GetTagListResponse extends OperationResponse {

    private Set<TagWrapper> tagList;

    public GetTagListResponse(Set<TagWrapper> tagList) {
        super(RESULT_CODE__OK, RESULT_MESSAGE__OK);
        this.tagList = tagList;
    }

    public Set<TagWrapper> getTagList() {
        if (tagList == null) {
            tagList = new HashSet<>();
        }
        return tagList;
    }

}
