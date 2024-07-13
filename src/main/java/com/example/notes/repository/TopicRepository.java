package com.example.notes.repository;

import com.example.notes.model.Topic;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.LockMode;
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

    public Topic create(Topic newTopic) {
        sessionFactory.getCurrentSession().persist(newTopic);
        return newTopic;
    }

    public boolean update(Topic newTopic) {
        Session session = sessionFactory.getCurrentSession();
        Topic storedTopic = session.get(Topic.class, newTopic.getId(), LockMode.PESSIMISTIC_READ);
        if (storedTopic == null) {
            return false;
        }
        session.merge(newTopic);
        return true;
    }

    public boolean delete(Integer topicId) {
        Session session = sessionFactory.getCurrentSession();
        Topic topicForDelete = session.get(Topic.class, topicId);
        if (topicForDelete == null) {
            return false;
        }
        session.remove(topicForDelete);
        return true;
    }

}
