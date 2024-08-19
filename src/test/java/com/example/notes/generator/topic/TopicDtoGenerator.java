package com.example.notes.generator.topic;

import com.example.notes.dto.topic.*;

import java.util.HashSet;
import java.util.Set;

public class TopicDtoGenerator {

    public static final Integer TOPIC_ID_1 = 1;
    public static final Integer TOPIC_ID_2 = 2;
    public static final String TOPIC_NAME_1 = "TOPIC 1";
    public static final String TOPIC_NAME_1_UPDATED = "TOPIC 1 UPDATED";
    public static final String TOPIC_NAME_2 = "TOPIC 2";

    public static GetTopicTreeResponse.GetTopicTreeResponseBuilder generateGetTopicTreeResponseBuilder() {
        Set<TopicWrapper> topicWrapperList = new HashSet<>();
        topicWrapperList.add(new TopicWrapper(TOPIC_ID_1, TOPIC_NAME_1, null));
        topicWrapperList.add(new TopicWrapper(TOPIC_ID_2, TOPIC_NAME_2, null));
        return GetTopicTreeResponse.builder().setTopicList(topicWrapperList);
    }

    public static CreateTopicRequest.CreateTopicRequestBuilder generateCreateTopicRequestBuilder() {
        return CreateTopicRequest.builder().setTopicName(TOPIC_NAME_1);
    }

    public static CreateTopicResponse.CreateTopicResponseBuilder generateCreateTopicResponseBuilder() {
        return CreateTopicResponse.builder().setTopicId(TOPIC_ID_1).setTopicName(TOPIC_NAME_1)
                .setResultCode(0).setResultMessage("OK");
    }

    public static UpdateTopicRequest.UpdateTopicRequestBuilder generateUpdateTopicRequestBuilder() {
        return UpdateTopicRequest.builder().setTopicId(TOPIC_ID_1).setTopicName(TOPIC_NAME_1_UPDATED);
    }

    public static DeleteTopicRequest.DeleteTopicRequestBuilder generateDeleteTopicRequestBuilder() {
        return DeleteTopicRequest.builder().setTopicId(TOPIC_ID_1);
    }

}
