package com.example.notes.service;

import com.example.notes.dto.tag.CreateTagResponse;
import com.example.notes.dto.tag.GetTagListResponse;
import com.example.notes.model.Tag;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TagService {

    public static final String DUPLICATE_TAG_NAME_MESSAGE = "Duplicate tag name: %s";

    private final Set<Tag> tagSet = new HashSet<>();
    private final AtomicInteger tagIdSequence = new AtomicInteger(0);

    public CreateTagResponse addTag(String tagName) {
        //todo check name is not null and not empty
        checkTagDuplicate(tagName);

        Tag tag = new Tag(tagIdSequence.incrementAndGet(),tagName);
        tagSet.add(tag);

        CreateTagResponse response = new CreateTagResponse();
        response.setTagName(tag.getName());
        response.setTagId(tag.getId());

        return response;
    }

    // check if tag with name "newTagName" already exists
    private void checkTagDuplicate(String newTagName) {
        if (findTagByName(newTagName).isPresent()) {
            throw new RuntimeException(String.format(DUPLICATE_TAG_NAME_MESSAGE, newTagName));
        }
    }

    private Optional<Tag> findTagById(Integer id) {
        return tagSet.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    private Optional<Tag> findTagByName(String tagName) {
        return tagSet.stream().filter(n -> n.getName().equals(tagName)).findFirst();
    }

    public GetTagListResponse getTagList() {
        GetTagListResponse response = new GetTagListResponse();
        response.setTagList(tagSet);

        return response;
    }

    public void updateTag(Tag updatedTag) {
        //todo check updatedTag id and name filled
        checkTagDuplicate(updatedTag.getName());

        Optional<Tag> storedTag = findTagById(updatedTag.getId());
        if (storedTag.isPresent()) {
            storedTag.get().setName(updatedTag.getName());
        } else {
            //todo raise exception tag with such tagId not found
        }
    }

    public void deleteTag(Integer tagId) {
        // todo check "tagId" is not null
        Optional<Tag> storedTag = findTagById(tagId);
        if (storedTag.isPresent()) {
            tagSet.remove(storedTag.get());
        } else {
            //todo raise exception tag with such tagId not found
        }
    }

}
