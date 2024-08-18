package com.example.notes.generator.topic;

import com.example.notes.model.Topic;

public class TopicGenerator {

    public static final Integer TOPIC_ID = 1;
    public static final String TOPIC_NAME = "TOPIC 1";

    public static Topic.TopicBuilder generateTopicAfterCreateBuilder() {
        return Topic.builder().setId(TOPIC_ID).setName(TOPIC_NAME);
    }

    public static Topic.TopicBuilder generateTopicAfterUpdateBuilder(String newNameField) {
        return generateTopicAfterCreateBuilder().setName(newNameField);
    }

}
