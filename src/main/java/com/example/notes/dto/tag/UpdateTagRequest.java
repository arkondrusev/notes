package com.example.notes.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class UpdateTagRequest {

    private Integer tagId;
    private String tagName;

}
