package com.example.notes.repository;

import com.example.notes.model.Topic;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Transactional
public class TopicRepository {

    private SessionFactory sessionFactory;

    public Optional<Topic> findById(Integer topicId) {
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Topic t where t.id = :topicId", Topic.class)
                .setParameter("topicId", topicId).stream().findFirst();
    }

    public Optional<Topic> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Topic t where t.name = :name", Topic.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }

    public Set<Topic> findListByParentId(Integer parentId) {
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Topic t where t.parentTopic.id= :parentId", Topic.class)
                .setParameter("parentId", parentId)
                .getResultStream().collect(Collectors.toSet());
    }

    public Set<Topic> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Topic t", Topic.class).getResultStream().collect(Collectors.toSet());
    }

    public Topic create(String name, Topic parent) {
        Topic newTopic = new Topic(null, name, parent);
        sessionFactory.getCurrentSession().persist(newTopic);
        //todo handle "duplicate tag" db error
        return newTopic;
    }

    public void update(Topic newTopic) {
        Topic topic = sessionFactory.getCurrentSession().get(Topic.class, newTopic.getId());
        topic.setName(newTopic.getName());
        topic.setParentTopic(newTopic.getParentTopic());
    }

    public void delete(Topic topic) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Topic.class, topic.getId()));
    }

}
