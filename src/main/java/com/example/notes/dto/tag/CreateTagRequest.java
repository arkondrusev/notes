package com.example.notes.dto.tag;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set")
public class CreateTagRequest {

    @NonNull
    private String tagName;

}
