package com.example.notes.dto.tag;

import com.example.notes.dto.OperationResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=true)
@SuperBuilder(setterPrefix = "set")
public class GetTagListResponse extends OperationResponse {

    private Set<TagWrapper> tagList;

    @Builder(setterPrefix = "set")
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
