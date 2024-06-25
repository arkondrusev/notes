package com.example.notes.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagWrapper {

    private Integer tagId;
    private String tagName;

}
