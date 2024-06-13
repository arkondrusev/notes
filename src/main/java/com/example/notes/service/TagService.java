package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.tag.*;
import com.example.notes.model.Tag;
import com.example.notes.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TagService {

    public static final String DUPLICATE_TAG_NAME_MESSAGE = "Duplicate tag name: %s";

    private final TagRepository tagRepository;

    private final Set<Tag> tagSet = new HashSet<>();

    public CreateTagResponse createTag(CreateTagRequest request) {
        //todo check name is not null and not empty
        checkTagDuplicate(request.getTagName());

        Tag tag = tagRepository.addTag(request.getTagName());

        return new CreateTagResponse(tag.getId(), tag.getName());
    }

    // check if tag with name "newTagName" already exists
    private void checkTagDuplicate(String newTagName) {
        if (findTagByName(newTagName).isPresent()) {
            throw new RuntimeException(String.format(DUPLICATE_TAG_NAME_MESSAGE, newTagName));
        }
    }

    public Optional<Tag> findTagById(Integer id) {
        return tagSet.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    private Optional<Tag> findTagByName(String tagName) {
        return tagSet.stream().filter(n -> n.getName().equals(tagName)).findFirst();
    }

    public GetTagListResponse getTagList() {
        return new GetTagListResponse(tagSet);
    }

    public OperationResponse updateTag(UpdateTagRequest request) {
        //todo check updatedTag id and name filled
        checkTagDuplicate(request.getTagName());

        Optional<Tag> storedTag = findTagById(request.getTagId());
        if (storedTag.isPresent()) {
            storedTag.get().setName(request.getTagName());
        } else {
            //todo raise exception tag with such tagId not found
        }

        return OperationResponse.ok();
    }

    public OperationResponse deleteTag(DeleteTagRequest request) {
        // todo check "tagId" is not null
        Optional<Tag> storedTag = findTagById(request.getTagId());
        if (storedTag.isPresent()) {
            tagSet.remove(storedTag.get());
        } else {
            //todo raise exception tag with such tagId not found
        }

        return OperationResponse.ok();
    }

}
