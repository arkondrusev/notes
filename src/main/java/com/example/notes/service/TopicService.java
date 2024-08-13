package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.topic.*;
import com.example.notes.mapper.Topic2CreateTopicResponseMapper;
import com.example.notes.mapper.Topic2TopicWrapperMapper;
import com.example.notes.mapper.UpdateTopicRequest2TopicMapper;
import com.example.notes.model.Topic;
import com.example.notes.repository.TopicRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.example.notes.dto.OperationResponse.RESULT_CODE__OK;
import static com.example.notes.dto.OperationResponse.RESULT_MESSAGE__OK;

@Service
@RequiredArgsConstructor
public class TopicService {

    public static final String PARENT_TOPIC_NOT_FOUND_MESSAGE = "Parent topic not found: id=%s";
    public static final String TOPIC_NOT_FOUND_MESSAGE = "Topic not found: id=%s";
    public static final String TOPIC_NAME_IS_EMPTY_MESSAGE = "Topic name is empty";
    public static final String TOPIC_ID_IS_EMPTY_MESSAGE = "Topic id is empty";

    private final TopicRepository topicRepository;

    public OperationResponse createTopic(@NonNull final CreateTopicRequest request) {
        Topic newTopic;
        try {
            checkCreateTopicRequestParams(request);

            newTopic = topicRepository.save(new Topic(null, request.getTopicName(),
                    getParentTopic(request.getParentTopicId())));
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        CreateTopicResponse response = Topic2CreateTopicResponseMapper.INSTANCE.topic2CreateTopicResponse(newTopic);
        response.setResultCode(RESULT_CODE__OK);
        response.setResultMessage(RESULT_MESSAGE__OK);
        return response;
    }

    public OperationResponse updateTopic(@NonNull final UpdateTopicRequest request) {
        try {
            checkUpdateTopicRequestParams(request);

            if (topicRepository.findById(request.getTopicId()).isEmpty()) {
                throw new RuntimeException(String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId()));
            }
            topicRepository.save(UpdateTopicRequest2TopicMapper.INSTANCE
                    .UpdateTopicRequest2Topic(request, getParentTopic(request.getParentTopicId())));
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return OperationResponse.ok();
    }

    public OperationResponse deleteTopic(@NonNull final DeleteTopicRequest request) {
        try {
            checkDeleteTopicRequestParams(request);

            Optional<Topic> topicOpt = topicRepository.findById(request.getTopicId());
            if (topicOpt.isEmpty()) {
                throw new RuntimeException(String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId()));
            }
            topicRepository.delete(topicOpt.get());
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }

        return OperationResponse.ok();
    }

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

    private Topic getParentTopic(Integer parentTopicId) {
        Topic parentTopic = null;
        if (parentTopicId != null) {
            parentTopic = topicRepository.findById(parentTopicId)
                    .orElseThrow(() -> new RuntimeException(
                            String.format(PARENT_TOPIC_NOT_FOUND_MESSAGE, parentTopicId)));
        }
        return parentTopic;
    }

}
