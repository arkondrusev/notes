package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.tag.*;
import com.example.notes.mapper.Tag2CreateTagResponseMapper;
import com.example.notes.mapper.Tag2TagWrapperMapper;
import com.example.notes.model.Tag;
import com.example.notes.repository.TagRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    public static final String DUPLICATE_TAG_NAME_MESSAGE = "Duplicate tag name: %s";
    public static final String NOT_FOUND_TAG_BY_ID_MESSAGE = "Tag not found. id=%s";

    private final TagRepository tagRepository;

    public OperationResponse createTag(@NonNull CreateTagRequest request) {
        Tag tag;
        try {
            //todo check name is not null and not empty
            checkTagDuplicate(request.getTagName());

            tag = tagRepository.create(request.getTagName());
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return Tag2CreateTagResponseMapper.INSTANCE.tag2CreateTagResponse(tag);
    }

    // check if tag with name "newTagName" already exists
    private void checkTagDuplicate(String newTagName) {
        if (tagRepository.findByName(newTagName).isPresent()) {
            throw new RuntimeException(String.format(DUPLICATE_TAG_NAME_MESSAGE, newTagName));
        }
    }

    public GetTagListResponse getTagList() {
        Set<Tag> allTags = tagRepository.findAll();
        HashSet<TagWrapper> tagWrapperList = new HashSet<>();
        allTags.forEach(tag -> tagWrapperList.add(Tag2TagWrapperMapper.INSTANCE.tag2TagWrapper(tag)));

        return new GetTagListResponse(tagWrapperList);
    }

    public OperationResponse updateTag(@NonNull UpdateTagRequest request) {
        try {
            //todo check updatedTag id and name filled
            checkTagDuplicate(request.getTagName());

            Tag foundTag = tagRepository.findById(request.getTagId())
                    .orElseThrow(() -> new RuntimeException(String.format(NOT_FOUND_TAG_BY_ID_MESSAGE, request.getTagId())));
            foundTag.setName(request.getTagName());
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return OperationResponse.ok();
    }

    public OperationResponse deleteTag(@NonNull DeleteTagRequest request) {
        try {
            // todo check "tagId" is not null

            Tag storedTag = tagRepository.findById(request.getTagId())
                    .orElseThrow(() -> new RuntimeException(String.format(NOT_FOUND_TAG_BY_ID_MESSAGE, request.getTagId())));
            tagRepository.delete(storedTag);
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return OperationResponse.ok();
    }

}
