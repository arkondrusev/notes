package com.example.notes.service;

import com.example.notes.dto.CreateTagResponse;
import com.example.notes.dto.GetTagListResponse;
import com.example.notes.model.Tag;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TagService {

    private final Set<Tag> tagSet = new HashSet<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public CreateTagResponse addTag(String name) {
        checkTagDuplicate(name);

        Tag tag = new Tag();
        tag.setName(name);
        tag.setId(idSequence.incrementAndGet());
        tagSet.add(tag);

        CreateTagResponse response = new CreateTagResponse();
        response.setTagName(tag.getName());
        response.setTagId(tag.getId());

        return response;
    }

    // check if tag with name "newTagName" already exists
    private void checkTagDuplicate(String newTagName) {
        if (tagSet.stream().filter(n -> n.getName().equals(newTagName))
                .findFirst().isPresent()) {
            throw new RuntimeException("Duplicate tag name");
        }
    }

    public GetTagListResponse getTagSet() {
        GetTagListResponse response = new GetTagListResponse();
        response.setTagSet(tagSet);

        return response;
    }

}
