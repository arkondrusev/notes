package com.example.notes.dto;

import com.example.notes.model.Tag;
import lombok.Data;

import java.util.List;

@Data
public class GetTagListResponse {

    private List<Tag> tagList;

}
