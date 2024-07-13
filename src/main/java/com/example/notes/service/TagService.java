package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.tag.*;
import com.example.notes.mapper.CreateTagRequest2TagMapper;
import com.example.notes.mapper.Tag2CreateTagResponseMapper;
import com.example.notes.mapper.Tag2TagWrapperMapper;
import com.example.notes.mapper.UpdateTagRequest2TagMapper;
import com.example.notes.model.Tag;
import com.example.notes.repository.TagRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    public static final String DUPLICATE_TAG_NAME_MESSAGE = "Duplicate tag name: %s";
    public static final String TAG_NOT_FOUND_BY_ID_MESSAGE = "Tag not found. id=%s";
    public static final String TAG_NAME_IS_EMPTY_MESSAGE = "Tag name is empty";
    public static final String TAG_ID_IS_EMPTY_MESSAGE = "Tag id is empty";

    private final TagRepository tagRepository;

    public OperationResponse createTag(@NonNull CreateTagRequest request) {
        Tag tag;
        try {
            checkCreateTagRequestParams(request);
            tag = tagRepository.create(CreateTagRequest2TagMapper.INSTANCE.CreateTagRequest2Tag(request));
        } catch (DataIntegrityViolationException e) {
            return OperationResponse.error(String.format(DUPLICATE_TAG_NAME_MESSAGE, request.getTagName()));
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return Tag2CreateTagResponseMapper.INSTANCE.tag2CreateTagResponse(tag);
    }

    private void checkCreateTagRequestParams(@NonNull CreateTagRequest request) {
        if (request.getTagName() == null || request.getTagName().isEmpty()) {
            throw new IllegalArgumentException(TAG_NAME_IS_EMPTY_MESSAGE);
        }
    }

    private void checkUpdateTagRequestParams(@NonNull UpdateTagRequest request) {
        if (request.getTagId() == null) {
            throw new IllegalArgumentException(TAG_ID_IS_EMPTY_MESSAGE);
        }
        if (request.getTagName() == null || request.getTagName().isEmpty()) {
            throw new IllegalArgumentException(TAG_NAME_IS_EMPTY_MESSAGE);
        }
    }

    private void checkDeleteTagRequestParams(@NonNull DeleteTagRequest request) {
        if (request.getTagId() == null) {
            throw new IllegalArgumentException(TAG_ID_IS_EMPTY_MESSAGE);
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
            checkUpdateTagRequestParams(request);
            if (!tagRepository.update(UpdateTagRequest2TagMapper.INSTANCE.UpdateTagRequest2Tag(request))) {
                throw new RuntimeException(String.format(TAG_NOT_FOUND_BY_ID_MESSAGE, request.getTagId()));
            }
        } catch (DataIntegrityViolationException e) {
            return OperationResponse.error(String.format(DUPLICATE_TAG_NAME_MESSAGE, request.getTagName()));
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return OperationResponse.ok();
    }

    public OperationResponse deleteTag(@NonNull DeleteTagRequest request) {
        try {
            checkDeleteTagRequestParams(request);
            if (!tagRepository.delete(request.getTagId())) {
                return OperationResponse.error(String.format(TAG_NOT_FOUND_BY_ID_MESSAGE, request.getTagId()));
            }
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return OperationResponse.ok();
    }

}
