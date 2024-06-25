package com.example.notes.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTagRequest {

    private String tagName;

}
