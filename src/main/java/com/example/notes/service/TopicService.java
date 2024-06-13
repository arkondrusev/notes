package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.topic.*;
import com.example.notes.model.Topic;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TopicService {

    public static final String DUPLICATE_TOPIC_NAME_MESSAGE = "Duplicate topic name: %s";

    private final Set<Topic> topicList = new HashSet<>();
    private final AtomicInteger topicIdSequence = new AtomicInteger(0);

    public CreateTopicResponse addTopic(CreateTopicRequest request) {
        //todo check "request" params are filled

        Topic newTopic;
        if (request.getParentTopicId() == null) {
            newTopic = new Topic(topicIdSequence.incrementAndGet(), request.getTopicName(), null);
        } else {
            Optional<Topic> parentTopicOpt = findTopicById(request.getParentTopicId());
            if (parentTopicOpt.isPresent()) {
                Topic parentTopic = parentTopicOpt.get();
                newTopic = new Topic(topicIdSequence.incrementAndGet(), request.getTopicName(), parentTopic);
                parentTopic.getChildrenTopicList().add(newTopic);
                topicList.add(newTopic);
            } else {
                throw new RuntimeException("Parent topic not found: id=" + request.getParentTopicId());
            }
        }

        return new CreateTopicResponse(newTopic.getId(), newTopic.getName(),
                newTopic.getParentTopic() == null ? null : newTopic.getParentTopic().getId());
    }

    public Optional<Topic> findTopicById(Integer topicId) {
        return topicList.stream()
                .filter(topic -> topic.getId().equals(topicId))
                .findFirst();
    }

    private Optional<Topic> findTopicByName(String topicName) {
        return topicList.stream()
                .filter(topic -> topic.getName().equals(topicName)).findFirst();
    }

    public GetTopicTreeResponse getTopicTree() {
        Set<TopicWrapper> rootList = new HashSet<>();

        topicList.stream()
                .filter(topic -> topic.getParentTopic() == null)
                .forEach(topic-> rootList.add(fillTopicWrapper(topic)));

        return new GetTopicTreeResponse(rootList);
    }

    private TopicWrapper fillTopicWrapper(Topic topic) {
        TopicWrapper topicWrapper = new TopicWrapper(topic.getId(), topic.getName()
                , topic.getParentTopic() == null ? null : topic.getParentTopic().getId());
        for (Topic child : topic.getChildrenTopicList()) {
            topicWrapper.getChildrenTopicList().add(fillTopicWrapper(child));
        }
        return topicWrapper;
    }

    public OperationResponse updateTopic(UpdateTopicRequest request) {
        // todo check request params

        Optional<Topic> topicOpt = findTopicById(request.getTopicId());
        if (topicOpt.isEmpty()) {
            throw new RuntimeException("Topic not found: id=" + request.getTopicId());
        }

        Topic topic = topicOpt.get();
        if (request.getParentTopicId() != null) {
            Optional<Topic> parentTopicOpt = findTopicById(request.getParentTopicId());
            if (parentTopicOpt.isEmpty()) {
                throw new RuntimeException("Parent topic not found: id=" + request.getParentTopicId());
            }
            topic.getParentTopic().getChildrenTopicList().remove(topic);
            Topic newParentTopic = parentTopicOpt.get();
            topic.setParentTopic(newParentTopic);
            newParentTopic.getChildrenTopicList().add(topic);
        }
        topic.setName(request.getTopicName());

        return OperationResponse.ok();
    }

    public OperationResponse deleteTopic(DeleteTopicRequest request) {
        //todo check "topicId" not empty
        Optional<Topic> topicOpt = findTopicById(request.getTopicId());
        if (topicOpt.isPresent()) {
            Topic topic = topicOpt.get();
            if (topic.getParentTopic() != null) {
                topic.getParentTopic().getChildrenTopicList().remove(topic);
            }
            topic.getChildrenTopicList().forEach(n -> n.setParentTopic(null));
            topicList.remove(topic);
        } else {
            //todo throw exception topic with such id not found
        }
        return OperationResponse.ok();
    }

}
