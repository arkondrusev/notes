package com.example.notes.service;

import com.example.notes.dto.topic.CreateTopicRequest;
import com.example.notes.dto.topic.CreateTopicResponse;
import com.example.notes.dto.topic.GetTopicTreeResponse;
import com.example.notes.model.Topic;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TopicService {

    private final Set<Topic> topicSet = new HashSet<>();
    private final AtomicInteger topicIdSequence = new AtomicInteger(0);

    public CreateTopicResponse addTopic(CreateTopicRequest request) {
        //todo check "request" params are filled
        checkTopicNameDuplicate(request.getName());

        Topic parentTopic = null;
        if (request.getParentTopicId() != null) {
            Optional<Topic> foundTopic = findTopicById(request.getParentTopicId());
            if (foundTopic.isPresent()) {
                parentTopic = foundTopic.get();
            } else {
                // todo raise Exception "parent topic with such id not found"
            }
        }

        Topic newTopic = new Topic(topicIdSequence.incrementAndGet(), request.getName(), parentTopic);
        topicSet.add(newTopic);

        CreateTopicResponse response = new CreateTopicResponse(newTopic.getId(), newTopic.getName(), newTopic.getParentTopic());

        return response;
    }

    private Optional<Topic> findTopicById(Integer topicId) {
        return topicSet.stream()
                .filter(topic -> topic.getId().equals(topicId))
                .findFirst();
    }

    private Optional<Topic> findTopicByName(String topicName) {
        return topicSet.stream()
                .filter(topic -> topic.getName().equals(topicName)).findFirst();
    }

    private void checkTopicNameDuplicate(String topicName) {
        if (findTopicByName(topicName).isPresent()) {
            throw new RuntimeException("Duplicate topic name: " + topicName);
        }
    }

    public GetTopicTreeResponse getTopicTree() {
        GetTopicTreeResponse response = new GetTopicTreeResponse();
        //todo implement method
        return response;
    }

    public void deleteTopic(Integer topicId) {
        //todo check "topicId" exists
        //todo implement method
    }

}
