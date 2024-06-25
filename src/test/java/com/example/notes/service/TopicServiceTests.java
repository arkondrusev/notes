package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.topic.CreateTopicRequest;
import com.example.notes.dto.topic.CreateTopicResponse;
import com.example.notes.dto.topic.DeleteTopicRequest;
import com.example.notes.dto.topic.UpdateTopicRequest;
import com.example.notes.model.Topic;
import com.example.notes.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        CreateTopicRequest request = new CreateTopicRequest(expectedTopicName1, null);
        CreateTopicResponse expectedResponse = new CreateTopicResponse(1, expectedTopicName1, null);
        when(topicRepository.create(expectedTopicName1, null))
                .thenReturn(new Topic(1, expectedTopicName1, null));

        OperationResponse actualResponse = topicService.createTopic(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getTopicTree_success() {
        /*
        Set<TopicWrapper> expectedWrapperList = new HashSet<>();
        TopicWrapper topicWrapper1 = new TopicWrapper(1, expectedTopicName1, null);
        expectedWrapperList.add(topicWrapper1);
        expectedWrapperList.add(new TopicWrapper(2, expectedTopicName2, null));
        expectedWrapperList.add(new TopicWrapper(3, expectedTopicName3, topicWrapper1.getTopicId()));
        Set<Topic> expectedTopicList = new HashSet<>();
        Topic topic1 = new Topic(1, expectedTopicName1, null);
        expectedTopicList.add(topic1);
        expectedTopicList.add(new Topic(2, expectedTopicName2, null));
        Topic topic3 = new Topic(3, expectedTopicName3, topic1);
        expectedTopicList.add(topic3);
        Set<Topic> expectedChildrenTopicList = new HashSet<>();
        expectedChildrenTopicList.add(topic3);
        GetTopicTreeResponse expectedResponse = new GetTopicTreeResponse(expectedWrapperList);
        when(topicRepository.findAllTopics()).thenReturn(expectedTopicList);
        when(topicRepository.findTopicsByParentId(topic1.getId())).thenReturn(expectedChildrenTopicList);

        GetTopicTreeResponse actualResponse = topicService.getTopicTree();

        assertEquals(expectedResponse, actualResponse);
         */
    }

    @Test
    void updateTopic_success() {
        UpdateTopicRequest request = new UpdateTopicRequest(1, expectedTopicName1, null);
        OperationResponse expectedResponse = OperationResponse.ok();
        when(topicRepository.findById(request.getTopicId()))
                .thenReturn(Optional.of(new Topic(1, expectedTopicName1, null)));

        OperationResponse actualResponse = topicService.updateTopic(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteTopic_success() {
        DeleteTopicRequest request = new DeleteTopicRequest(1);
        OperationResponse expectedResponse = OperationResponse.ok();
        when(topicRepository.findById(request.getTopicId()))
                .thenReturn(Optional.of(new Topic(1, expectedTopicName1, null)));
        when(topicRepository.findListByParentId(request.getTopicId()))
                .thenReturn(new HashSet<>());

        OperationResponse actualResponse = topicService.deleteTopic(request);

        assertEquals(expectedResponse, actualResponse);
    }

}
