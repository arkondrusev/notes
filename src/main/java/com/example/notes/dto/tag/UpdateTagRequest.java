package com.example.notes.dto.tag;

import lombok.Data;

@Data
public class UpdateTagRequest {

    private Integer tagId;
    private String tagName;

}
