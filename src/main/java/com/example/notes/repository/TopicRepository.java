package com.example.notes.repository;

import com.example.notes.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class TopicRepository {

    private final Set<Topic> topicList = new HashSet<>();
    private final AtomicInteger topicIdSequence = new AtomicInteger(0);

    public Optional<Topic> findTopicById(Integer topicId) {
        return topicList.stream()
                .filter(topic -> topic.getId().equals(topicId))
                .findFirst();
    }

    public Optional<Topic> findTopicByName(String topicName) {
        return topicList.stream()
                .filter(topic -> topic.getName().equals(topicName)).findFirst();
    }

    public Set<Topic> findTopicsByParentId(Integer parentId) {
        return topicList.stream()
                .filter(topic -> topic.getParentTopic() != null
                        && topic.getParentTopic().getId().equals(parentId))
                .collect(Collectors.toSet());
    }

    public Set<Topic> findAllTopics() {
        return topicList;
    }

    public Topic addTopic(String topicName, Topic parentTopic) {
        Topic newTopic = new Topic(topicIdSequence.incrementAndGet(), topicName, parentTopic);
        topicList.add(newTopic);

        return newTopic;
    }

    public void deleteTopic(Topic topic) {
        topicList.remove(topic);
    }

}
