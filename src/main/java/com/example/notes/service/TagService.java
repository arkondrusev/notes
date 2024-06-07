package com.example.notes.service;

import com.example.notes.dto.CreateTagResponse;
import com.example.notes.dto.GetTagListResponse;
import com.example.notes.model.Tag;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TagService {

    private final Set<Tag> tagSet = new HashSet<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public CreateTagResponse addTag(String name) {
        //todo check name is not null and not empty
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
        if (findTagByName(newTagName).isPresent()) {
            throw new RuntimeException("Duplicate tag name");
        }
    }

    private Optional<Tag> findTagById(Integer id) {
        return tagSet.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    private Optional<Tag> findTagByName(String tagName) {
        return tagSet.stream().filter(n -> n.getName().equals(tagName)).findFirst();
    }

    public GetTagListResponse getTagSet() {
        GetTagListResponse response = new GetTagListResponse();
        response.setTagSet(tagSet);

        return response;
    }

    public void updateTag(Tag updatedTag) {
        //todo check updatedTag id and name filled
        checkTagDuplicate(updatedTag.getName());

        Optional<Tag> storedTag = findTagById(updatedTag.getId());
        if (storedTag.isPresent()) {
            storedTag.get().setName(updatedTag.getName());
        } else {
            //todo raise exception tag with such id not found
        }
    }

    public void deleteTag(Integer tagId) {
        // todo check id is not null
        Optional<Tag> storedTag = findTagById(tagId);
        if (storedTag.isPresent()) {
            tagSet.remove(storedTag.get());
        } else {
            //todo raise exception tag with such id not found
        }
    }

}
