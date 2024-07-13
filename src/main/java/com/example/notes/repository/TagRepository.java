package com.example.notes.repository;

import com.example.notes.model.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
@Transactional
public class TagRepository {

    private SessionFactory sessionFactory;

    public Optional<Tag> findById(Integer id) {
        HashSet<Integer> set = new HashSet<>();
        set.add(id);
        return findListByIdList(set).stream().findFirst();
    }

    public Set<Tag> findListByIdList(Set<Integer> idList) {
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Tag t where t.id in (:idList)", Tag.class)
                .setParameter("idList", idList)
                .getResultStream().collect(Collectors.toSet());
    }

    public Optional<Tag> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Tag t where t.name = :name", Tag.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }

    public Set<Tag> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("select t from Tag t", Tag.class).getResultStream().collect(Collectors.toSet());
    }

    public Tag create(Tag newTag) {
        sessionFactory.getCurrentSession().persist(newTag);
        return newTag;
    }

    public boolean update(Tag newTag) {
        Session session = sessionFactory.getCurrentSession();
        Tag storedTag = session.get(Tag.class, newTag.getId(), LockMode.PESSIMISTIC_READ);
        if (storedTag == null) {
            return false;
        }
        session.merge(newTag);
        return true;
    }

    public boolean delete(Integer tagId) {
        Session session = sessionFactory.getCurrentSession();
        Tag tagForDelete = session.get(Tag.class, tagId);
        if (tagForDelete == null) {
            return false;
        }
        session.remove(tagForDelete);
        return true;
    }

}
