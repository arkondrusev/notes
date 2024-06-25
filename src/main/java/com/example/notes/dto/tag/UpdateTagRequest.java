package com.example.notes.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTagRequest {

    private Integer tagId;
    private String tagName;

}
