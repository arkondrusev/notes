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

    private final Set<Topic> list = new HashSet<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public Optional<Topic> findById(Integer topicId) {
        return list.stream()
                .filter(topic -> topic.getId().equals(topicId))
                .findFirst();
    }

    public Optional<Topic> findByName(String name) {
        return list.stream()
                .filter(topic -> topic.getName().equals(name)).findFirst();
    }

    public Set<Topic> findListByParentId(Integer parentId) {
        return list.stream()
                .filter(topic -> topic.getParentTopic() != null
                        && topic.getParentTopic().getId().equals(parentId))
                .collect(Collectors.toSet());
    }

    public Set<Topic> findAll() {
        return list;
    }

    public Topic create(String name, Topic parent) {
        Topic newTopic = new Topic(idSequence.incrementAndGet(), name, parent);
        list.add(newTopic);

        return newTopic;
    }

    public void delete(Topic topic) {
        list.remove(topic);
    }

}
