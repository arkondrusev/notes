package com.example.notes.service;

import com.example.notes.dto.CreateTagResponse;
import com.example.notes.dto.GetTagListResponse;
import com.example.notes.model.Tag;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TagService {

    private final List<Tag> tagList = new ArrayList<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public CreateTagResponse addTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setId(idSequence.incrementAndGet());
        tagList.add(tag);

        CreateTagResponse response = new CreateTagResponse();
        response.setTagName(tag.getName());
        response.setTagId(tag.getId());

        return response;
    }

    public GetTagListResponse getTagList() {
        GetTagListResponse response = new GetTagListResponse();
        response.setTagList(tagList);

        return response;
    }

}
