package com.example.notes.dto.tag;

import com.example.notes.model.Tag;
import lombok.Data;

import java.util.Set;

@Data
public class GetTagListResponse {

    private Set<Tag> tagList;

}
