package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.topic.*;
import com.example.notes.mapper.Topic2CreateTopicResponseMapper;
import com.example.notes.mapper.Topic2TopicWrapperMapper;
import com.example.notes.model.Topic;
import com.example.notes.repository.TopicRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TopicService {

    public static final String PARENT_TOPIC_NOT_FOUND_MESSAGE = "Parent topic not found: id=%s";
    public static final String TOPIC_NOT_FOUND_MESSAGE = "Topic not found: id=%s";

    private final TopicRepository topicRepository;

    public GetTopicTreeResponse getTopicTree() {
        Set<TopicWrapper> rootList = new HashSet<>();
        topicRepository.findAllTopics().stream()
                .filter(topic -> topic.getParentTopic() == null)
                .forEach(topic-> rootList.add(Topic2TopicWrapperMapper.INSTANCE.topic2TopicWrapper(topic)));

        return new GetTopicTreeResponse(rootList);
    }

    public CreateTopicResponse createTopic(@NonNull final CreateTopicRequest request) {
        //todo check "request" params are filled

        Topic parentTopic = null;
        if (request.getParentTopicId() != null) {
            parentTopic = topicRepository.findTopicById(request.getParentTopicId())
                    .orElseThrow(()-> new RuntimeException(
                            String.format(PARENT_TOPIC_NOT_FOUND_MESSAGE, request.getParentTopicId())));
        }

        return Topic2CreateTopicResponseMapper.INSTANCE
                .topic2CreateTopicResponse(topicRepository.createTopic(request.getTopicName(), parentTopic));
    }

    public OperationResponse updateTopic(@NonNull final UpdateTopicRequest request) {
        // todo check request params

        Topic foundTopic = topicRepository.findTopicById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException(
                        String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId())));
        foundTopic.setName(request.getTopicName());
        // parent topic update
        if (request.getParentTopicId() != null) {
            Topic newParentTopic = topicRepository.findTopicById(request.getParentTopicId())
                    .orElseThrow(() -> new RuntimeException(
                            String.format(PARENT_TOPIC_NOT_FOUND_MESSAGE, request.getParentTopicId())));
            foundTopic.setParentTopic(newParentTopic);
        } else {
            foundTopic.setParentTopic(null);
        }

        return OperationResponse.ok();
    }

    public OperationResponse deleteTopic(@NonNull final DeleteTopicRequest request) {
        //todo check "topicId" not empty

        Topic foundTopic = topicRepository.findTopicById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException(
                        String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId())));
        topicRepository.findTopicsByParentId(foundTopic.getParentTopic().getId())
                .forEach(t -> t.setParentTopic(null));

        topicRepository.deleteTopic(foundTopic);

        return OperationResponse.ok();
    }

}
