package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.tag.*;
import com.example.notes.mapper.Tag2TagWrapperMapper;
import com.example.notes.model.Tag;
import com.example.notes.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.notes.service.TagService.DUPLICATE_TAG_NAME_MESSAGE;
import static com.example.notes.service.TagService.NOT_FOUND_TAG_BY_ID_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TagService.class
})
public class TagServiceTests {

    @Autowired
    private TagService tagService;

    private final String expectedTagName1 = "TestTagName1";
    private final String expectedTagName2 = "TestTagName2";

    @MockBean
    private TagRepository tagRepository;

    @Test
    void createTag_success() {
        CreateTagRequest request = new CreateTagRequest(expectedTagName1);
        OperationResponse expectedResponse = new CreateTagResponse(1, expectedTagName1);
        when(tagRepository.create(expectedTagName1)).thenReturn(new Tag(1, expectedTagName1));
        when(tagRepository.create(expectedTagName1)).thenReturn(new Tag(1, expectedTagName1));

        OperationResponse actualResponse = tagService.createTag(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createTag_fail_duplicateTagException() {
        CreateTagRequest request = new CreateTagRequest(expectedTagName1);
        OperationResponse expectedResponse = OperationResponse.error(String.format(DUPLICATE_TAG_NAME_MESSAGE, expectedTagName1));
        when(tagRepository.findByName(expectedTagName1))
                .thenReturn(Optional.of(new Tag(1, expectedTagName1)));

        OperationResponse actualResponse = tagService.createTag(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getTagList_success() {
        Set<Tag> tagList = new HashSet<>();
        tagList.add(new Tag(1, expectedTagName1));
        tagList.add(new Tag(2, expectedTagName2));
        GetTagListResponse expectedResponse = new GetTagListResponse(Tag2TagWrapperMapper
                .INSTANCE.tag2TagWrapperList(tagList));

        when(tagRepository.findAll()).thenReturn(tagList);

        OperationResponse actualResponse = tagService.getTagList();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateTag_success() {
        Tag tag = new Tag(1, expectedTagName1);
        UpdateTagRequest request = new UpdateTagRequest(1, expectedTagName1);
        OperationResponse expectedResponse = OperationResponse.ok();
        when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));

        OperationResponse actualResponse = tagService.updateTag(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateTag_fail_duplicateTagException() {
        Tag tag = new Tag(1, expectedTagName1);
        UpdateTagRequest request = new UpdateTagRequest(1, expectedTagName1);
        OperationResponse expectedResponse = OperationResponse.error(String.format(DUPLICATE_TAG_NAME_MESSAGE, expectedTagName1));
        when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));
        when(tagRepository.findByName(expectedTagName1)).thenReturn(Optional.of(tag));

        OperationResponse actualResponse = tagService.updateTag(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteTag_success() {
        Tag tag = new Tag(1, expectedTagName1);
        DeleteTagRequest request = new DeleteTagRequest(tag.getId());
        OperationResponse expectedResponse = OperationResponse.ok();
        when(tagRepository.findById(tag.getId())).thenReturn(Optional.of(tag));

        OperationResponse actualResponse = tagService.deleteTag(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteTag_fail_tagNotFoundException() {
        Integer tagId = 1;
        DeleteTagRequest request = new DeleteTagRequest(tagId);
        OperationResponse expectedResponse = OperationResponse.error(String.format(NOT_FOUND_TAG_BY_ID_MESSAGE, tagId));
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        OperationResponse actualResponse = tagService.deleteTag(request);

        assertEquals(expectedResponse, actualResponse);
    }

}
