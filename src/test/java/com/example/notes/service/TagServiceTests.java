package com.example.notes.service;

import com.example.notes.dto.CreateTagResponse;
import com.example.notes.dto.GetTagListResponse;
import com.example.notes.model.Tag;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TagService.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TagServiceTests {

    @Autowired
    private TagService tagService;

    private String expectedTagName1 = "TestTag1";
    private String expectedTagName2 = "TestTag2";

    @Test
    @Order(1)
    void addTag_success() {
        tagService.addTag(expectedTagName1);
        CreateTagResponse createTagResponse = tagService.addTag(expectedTagName2);

        Assertions.assertNotNull(createTagResponse);
        Assertions.assertNotNull(createTagResponse.getTagId());
        Assertions.assertEquals(expectedTagName2,createTagResponse.getTagName());
    }

    @Test
    @Order(2)
    void addTag_fail_duplicateTagException() {
        String expectedExceptionMessage = "Duplicate tag name";
        Throwable actualException = Assertions.assertThrows(RuntimeException.class,
                () -> tagService.addTag(expectedTagName2));

        Assertions.assertNotNull(actualException);
        Assertions.assertInstanceOf(RuntimeException.class,actualException);
        Assertions.assertEquals(expectedExceptionMessage,actualException.getMessage());
    }

    @Test
    @Order(3)
    void getTagList_success() {
        GetTagListResponse expectedResponse = new GetTagListResponse();
        expectedResponse.setTagSet(new HashSet<>());
        expectedResponse.getTagSet().add(new Tag(1, expectedTagName1));
        expectedResponse.getTagSet().add(new Tag(2, expectedTagName2));

        GetTagListResponse actualResponse = tagService.getTagList();
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(expectedResponse,actualResponse);
    }

    @Test
    @Order(4)
    void updateTag_success() {
        String expectedTagName = "TestTag2Updated";
        Tag expectedTag = new Tag(2, expectedTagName);
        tagService.updateTag(expectedTag);

        Optional<Tag> actualTag = tagService.getTagList().getTagSet().stream()
                .filter(n -> n.getId().equals(expectedTag.getId())).findFirst();

        Assertions.assertNotNull(actualTag);
        Assertions.assertTrue(actualTag.isPresent());
        Assertions.assertEquals(expectedTag.getName(),actualTag.get().getName());
    }

    @Test
    void updateTag_fail_duplicateTagException() {
        //todo implement the test
    }

    @Test
    void deleteTag_success() {
        //todo implement the test
    }

}
