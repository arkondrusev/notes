package com.example.notes.service;

import com.example.notes.dto.topic.CreateTopicRequest;
import com.example.notes.dto.topic.CreateTopicResponse;
import com.example.notes.dto.topic.GetTopicTreeResponse;
import com.example.notes.dto.topic.TopicWrapper;
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
        checkTopicNameDuplicate(request.getTopicName());

        Topic parentTopic = null;
        if (request.getParentTopicId() != null) {
            Optional<Topic> parentTopicOpt = findTopicById(request.getParentTopicId());
            if (parentTopicOpt.isPresent()) {
                parentTopic = parentTopicOpt.get();
            } else {
                // todo raise Exception "parent topic with such id not found"
            }
        }

        Topic newTopic = new Topic(topicIdSequence.incrementAndGet(), request.getTopicName(), parentTopic);
        if (parentTopic != null) {
            parentTopic.getChildrenTopicList().add(newTopic);
        }
        topicList.add(newTopic);

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

    private void checkTopicNameDuplicate(String topicName) {
        if (findTopicByName(topicName).isPresent()) {
            throw new RuntimeException(String.format(DUPLICATE_TOPIC_NAME_MESSAGE, topicName));
        }
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

    public void deleteTopic(Integer topicId) {
        //todo check "topicId" not empty
        Optional<Topic> topicOpt = findTopicById(topicId);
        if (topicOpt.isPresent()) {
            Topic topic = topicOpt.get();
            topic.getParentTopic().getChildrenTopicList().remove(topic);
            topic.getChildrenTopicList().forEach(n -> n.setParentTopic(null));
            topicList.remove(topic);
        } else {
            //todo throw exception topic with such id not found
        }
    }

}
