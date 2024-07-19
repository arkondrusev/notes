package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.topic.*;
import com.example.notes.mapper.Topic2CreateTopicResponseMapper;
import com.example.notes.mapper.Topic2TopicWrapperMapper;
import com.example.notes.mapper.UpdateTopicRequest2TopicMapper;
import com.example.notes.model.Topic;
import com.example.notes.repository.TopicRepositoryJPA;
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
    public static final String TOPIC_NAME_IS_EMPTY_MESSAGE = "Topic name is empty";
    public static final String TOPIC_ID_IS_EMPTY_MESSAGE = "Topic id is empty";

    private final TopicRepositoryJPA topicRepository;

    public OperationResponse getTopicTree() {
        Set<TopicWrapper> rootList = new HashSet<>();
        topicRepository.findAll().stream()
                .filter(topic -> topic.getParentTopic() == null)
                .forEach(topic-> rootList.add(getTopicWrapper(topic)));
        return new GetTopicTreeResponse(rootList);
    }

    private TopicWrapper getTopicWrapper(Topic topic) {
        return Topic2TopicWrapperMapper.INSTANCE.topic2TopicWrapper(topic, getChildrenTopicWrapperList(topic));
    }

    private Set<TopicWrapper> getChildrenTopicWrapperList(Topic topic) {
        Set<TopicWrapper> childrenTopicList = new HashSet<>();
        topicRepository.findByParentTopic(topic).forEach(child -> childrenTopicList.add(getTopicWrapper(child)));
        return childrenTopicList;
    }

    public void checkCreateTopicRequestParams(CreateTopicRequest request) {
        if (request.getTopicName() == null || request.getTopicName().isEmpty()) {
            throw new IllegalArgumentException(TOPIC_NAME_IS_EMPTY_MESSAGE);
        }
    }

    public void checkUpdateTopicRequestParams(UpdateTopicRequest request) {
        if (request.getTopicId() == null) {
            throw new IllegalArgumentException(TOPIC_ID_IS_EMPTY_MESSAGE);
        }
        if (request.getTopicName() == null || request.getTopicName().isEmpty()) {
            throw new IllegalArgumentException(TOPIC_NAME_IS_EMPTY_MESSAGE);
        }
    }

    public void checkDeleteTopicRequestParams(DeleteTopicRequest request) {
        if (request.getTopicId() == null) {
            throw new IllegalArgumentException(TOPIC_ID_IS_EMPTY_MESSAGE);
        }
    }

    public OperationResponse createTopic(@NonNull final CreateTopicRequest request) {
        Topic newTopic;
        try {
            checkCreateTopicRequestParams(request);
            Topic parentTopic = null;
            if (request.getParentTopicId() != null) {
                parentTopic = topicRepository.findById(request.getParentTopicId())
                        .orElseThrow(() -> new RuntimeException(
                                String.format(PARENT_TOPIC_NOT_FOUND_MESSAGE, request.getParentTopicId())));
            }
            newTopic = topicRepository.save(new Topic(null, request.getTopicName(), parentTopic));
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return Topic2CreateTopicResponseMapper.INSTANCE.topic2CreateTopicResponse(newTopic);
    }

    public OperationResponse updateTopic(@NonNull final UpdateTopicRequest request) {
        try {
            checkUpdateTopicRequestParams(request);
            Topic newParentTopic = null;
            if (request.getParentTopicId() != null) {
                newParentTopic = topicRepository.findById(request.getParentTopicId())
                        .orElseThrow(() -> new RuntimeException(
                                String.format(PARENT_TOPIC_NOT_FOUND_MESSAGE, request.getParentTopicId())));
            }
            topicRepository.save(UpdateTopicRequest2TopicMapper.INSTANCE.UpdateTopicRequest2Topic(request, newParentTopic));
            /*if (!) {
                throw new RuntimeException(String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId()));
            }*/
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return OperationResponse.ok();
    }

    public OperationResponse deleteTopic(@NonNull final DeleteTopicRequest request) {
        try {
            checkDeleteTopicRequestParams(request);
            topicRepository.delete(topicRepository.findById(request.getTopicId()).orElse(null));
            /*if (!topicRepository.delete(topicRepository.findById(request.getTopicId())) {
                return OperationResponse.error(String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId()));
            }*/
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return OperationResponse.ok();
    }

}
