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
import java.util.stream.Collectors;

@Service
public class TopicService {

    public static final String DUPLICATE_TOPIC_NAME_MESSAGE = "Duplicate topic name: %s";

    private final Set<Topic> topicList = new HashSet<>();
    private final AtomicInteger topicIdSequence = new AtomicInteger(0);

    public CreateTopicResponse addTopic(CreateTopicRequest request) {
        //todo check "request" params are filled
        checkTopicNameDuplicate(request.getName());

        Topic parentTopic = null;
        if (request.getParentTopicId() != null) {
            Optional<Topic> parentTopicOpt = findTopicById(request.getParentTopicId());
            if (parentTopicOpt.isPresent()) {
                parentTopic = parentTopicOpt.get();
            } else {
                // todo raise Exception "parent topic with such id not found"
            }
        }

        Topic newTopic = new Topic(topicIdSequence.incrementAndGet(), request.getName(), parentTopic);
        if (parentTopic != null) {
            parentTopic.getChildren().add(newTopic);
        }
        topicList.add(newTopic);

        CreateTopicResponse response = new CreateTopicResponse(newTopic.getId(), newTopic.getName(), newTopic.getParentTopic());

        return response;
    }

    private Optional<Topic> findTopicById(Integer topicId) {
        return topicList.stream()
                .filter(topic -> topic.getId().equals(topicId))
                .findFirst();
    }

    private Optional<Topic> findTopicByName(String topicName) {
        return topicList.stream()
                .filter(topic -> topic.getName().equals(topicName)).findFirst();
    }

    private void checkTopicNameDuplicate(String topicName) {
        if (findTopicByName(topicName).isPresent()) {
            throw new RuntimeException(String.format(DUPLICATE_TOPIC_NAME_MESSAGE, topicName));
        }
    }

    public GetTopicTreeResponse getTopicTree() {
        return new GetTopicTreeResponse(topicList.stream()
                .filter(n -> n.getParentTopic() == null).collect(Collectors.toSet()));
    }

    public void deleteTopic(Integer topicId) {
        //todo check "topicId" exists
        //todo implement method
    }

}
