package com.example.notes.repository;

import com.example.notes.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepositoryJPA extends JpaRepository<Topic, Integer> {

    public List<Topic> findByParentTopic(Topic parentTopic);

}
