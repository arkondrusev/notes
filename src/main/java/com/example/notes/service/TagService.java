package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.tag.*;
import com.example.notes.mapper.Tag2WrapperMapper;
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

    private final Tag2WrapperMapper tagMapper;

    public CreateTagResponse createTag(@NonNull CreateTagRequest request) {
        //todo check name is not null and not empty
        checkTagDuplicate(request.getTagName());

        Tag tag = tagRepository.addTag(request.getTagName());

        return new CreateTagResponse(tag.getId(), tag.getName());
    }

    // check if tag with name "newTagName" already exists
    private void checkTagDuplicate(String newTagName) {
        if (tagRepository.findTagByName(newTagName).isPresent()) {
            throw new RuntimeException(String.format(DUPLICATE_TAG_NAME_MESSAGE, newTagName));
        }
    }

    public GetTagListResponse getTagList() {
        Set<Tag> allTags = tagRepository.findAllTags();
        HashSet<TagWrapper> tagWrapperList = new HashSet<>();
        allTags.forEach(tag -> tagWrapperList.add(tagMapper.tag2TagWrapper(tag)));

        return new GetTagListResponse(tagWrapperList);
    }

    public OperationResponse updateTag(@NonNull UpdateTagRequest request) {
        //todo check updatedTag id and name filled
        checkTagDuplicate(request.getTagName());

        Tag foundTag = tagRepository.findTagById(request.getTagId())
                .orElseThrow(() -> new RuntimeException(String.format(NOT_FOUND_TAG_BY_ID_MESSAGE, request.getTagId())));
        foundTag.setName(request.getTagName());

        return OperationResponse.ok();
    }

    public OperationResponse deleteTag(@NonNull DeleteTagRequest request) {
        // todo check "tagId" is not null

        Tag storedTag = tagRepository.findTagById(request.getTagId())
                .orElseThrow(() -> new RuntimeException(String.format(NOT_FOUND_TAG_BY_ID_MESSAGE, request.getTagId())));
        tagRepository.deleteTag(storedTag);

        return OperationResponse.ok();
    }

}
