package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.topic.*;
import com.example.notes.model.Topic;
import com.example.notes.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static com.example.notes.service.TopicService.TOPIC_NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TopicService.class
})
public class TopicServiceTests {

    private final static String expectedTopicName1 = "Test Topic 1";

    @Autowired
    private TopicService topicService;

    @MockBean
    private TopicRepository topicRepository;

    @Test
    void createTopic_success() {
        CreateTopicRequest request = CreateTopicRequest.builder().setTopicName(expectedTopicName1).build();
        CreateTopicResponse expectedResponse = new CreateTopicResponse(1, expectedTopicName1, null);
        Topic topicForSave = new Topic(null, expectedTopicName1, null);
        Topic topicSaved = new Topic(1, expectedTopicName1, null);
        when(topicRepository.save(topicForSave)).thenReturn(topicSaved);

        assertEquals(expectedResponse, topicService.createTopic(request));
    }

    @Test
    void createTopic_fail__exceptionOnDB() {
        CreateTopicRequest request = CreateTopicRequest.builder().setTopicName(expectedTopicName1).build();
        when(topicRepository.save(any())).thenThrow(new RuntimeException("Test DB error"));

        assertEquals(OperationResponse.error("Test DB error"), topicService.createTopic(request));
    }

    @Test
    void getTopicTree_success() {
        Set<TopicWrapper> expectedWrapperList = new HashSet<>();
        TopicWrapper topicWrapper1 = new TopicWrapper(1, expectedTopicName1, null);
        expectedWrapperList.add(topicWrapper1);
        List<Topic> dbTopicList = new ArrayList<>();
        dbTopicList.add(new Topic(1, expectedTopicName1, null));
        when(topicRepository.findAll()).thenReturn(dbTopicList);

        assertEquals(new GetTopicTreeResponse(expectedWrapperList), topicService.getTopicTree());
    }

    @Test
    void updateTopic_success() {
        Topic topic = new Topic(1, expectedTopicName1, null);
        UpdateTopicRequest request = new UpdateTopicRequest(1, expectedTopicName1, null);
        when(topicRepository.findById(topic.getId())).thenReturn(Optional.of(topic));
        when(topicRepository.save(topic)).thenReturn(topic);

        assertEquals(OperationResponse.ok(), topicService.updateTopic(request));
    }

    @Test
    void updateTopic_fail__topicNotFoundException() {
        Integer topicId = 1;
        UpdateTopicRequest request = new UpdateTopicRequest(topicId, expectedTopicName1, null);
        OperationResponse expectedResponse = OperationResponse.error(String.format(TOPIC_NOT_FOUND_MESSAGE, topicId));
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        assertEquals(expectedResponse, topicService.updateTopic(request));
    }

    @Test
    void deleteTopic_success() {
        DeleteTopicRequest request = new DeleteTopicRequest(1);
        Topic topic = new Topic(1, expectedTopicName1, null);
        when(topicRepository.findById(request.getTopicId())).thenReturn(Optional.of(topic));

        assertEquals(OperationResponse.ok(), topicService.deleteTopic(request));
    }

    @Test
    void deleteTopic_fail__topicNotFoundException() {
        DeleteTopicRequest request = new DeleteTopicRequest(1);
        when(topicRepository.findById(request.getTopicId())).thenReturn(Optional.empty());

        assertEquals(OperationResponse.error(String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId())),
                topicService.deleteTopic(request));
    }

}
