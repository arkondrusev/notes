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
import java.util.Set;

import static com.example.notes.service.TagService.DUPLICATE_TAG_NAME_MESSAGE;
import static com.example.notes.service.TagService.TAG_NOT_FOUND_BY_ID_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    //@Test
    void createTag_success() {
        CreateTagRequest request = new CreateTagRequest(expectedTagName1);
        OperationResponse expectedResponse = new CreateTagResponse(1, expectedTagName1);
        Tag tagForSave = new Tag(null, expectedTagName1);
        Tag tagSaved = new Tag(1, expectedTagName1);
//        when(tagRepository.create(tagForSave)).thenReturn(tagSaved);

        assertEquals(expectedResponse, tagService.createTag(request));
    }

    //@Test
    void createTag_fail_duplicateTagException() {
        CreateTagRequest request = new CreateTagRequest(expectedTagName1);
        OperationResponse expectedResponse = OperationResponse.error(String.format(DUPLICATE_TAG_NAME_MESSAGE, expectedTagName1));
        Tag tagForSave = new Tag(null, expectedTagName1);
//        when(tagRepository.create(tagForSave)).thenThrow(DataIntegrityViolationException.class);

        assertEquals(expectedResponse, tagService.createTag(request));
    }

    @Test
    void getTagList_success() {
        Set<Tag> tagList = new HashSet<>();
        tagList.add(new Tag(1, expectedTagName1));
        tagList.add(new Tag(2, expectedTagName2));
        GetTagListResponse expectedResponse = new GetTagListResponse(Tag2TagWrapperMapper
                .INSTANCE.tag2TagWrapperList(tagList));
//        when(tagRepository.findAll()).thenReturn(tagList);

        assertEquals(expectedResponse, tagService.getTagList());
    }

    @Test
    void updateTag_success() {
        Tag tag = new Tag(1, expectedTagName1);
        UpdateTagRequest request = new UpdateTagRequest(1, expectedTagName1);
//        when(tagRepository.update(tag)).thenReturn(Boolean.TRUE);

        assertEquals(OperationResponse.ok(), tagService.updateTag(request));
    }

    @Test
    void updateTag_fail_duplicateTagException() {
        Tag tag = new Tag(1, expectedTagName1);
        UpdateTagRequest request = new UpdateTagRequest(1, expectedTagName1);
        OperationResponse expectedResponse = OperationResponse.error(String.format(DUPLICATE_TAG_NAME_MESSAGE, expectedTagName1));
//        when(tagRepository.update(tag)).thenThrow(DataIntegrityViolationException.class);

        assertEquals(expectedResponse, tagService.updateTag(request));
    }

    @Test
    void updateTag_fail_notFoundTagException() {
        Integer tagId = 1;
        UpdateTagRequest request = new UpdateTagRequest(tagId, expectedTagName1);
        OperationResponse expectedResponse = OperationResponse.error(String.format(TAG_NOT_FOUND_BY_ID_MESSAGE, tagId));
//        when(tagRepository.update(new Tag(tagId, expectedTagName1))).thenReturn(Boolean.FALSE);

        assertEquals(expectedResponse, tagService.updateTag(request));
    }

    @Test
    void deleteTag_success() {
        Tag tag = new Tag(1, expectedTagName1);
        DeleteTagRequest request = new DeleteTagRequest(tag.getId());
//        when(tagRepository.delete(tag.getId())).thenReturn(Boolean.TRUE);

        assertEquals(OperationResponse.ok(), tagService.deleteTag(request));
    }

    @Test
    void deleteTag_fail_tagNotFoundException() {
        Integer tagId = 1;
        DeleteTagRequest request = new DeleteTagRequest(tagId);
        OperationResponse expectedResponse = OperationResponse.error(String.format(TAG_NOT_FOUND_BY_ID_MESSAGE, tagId));
//        when(tagRepository.delete(tagId)).thenReturn(Boolean.FALSE);

        assertEquals(expectedResponse, tagService.deleteTag(request));
    }

}
