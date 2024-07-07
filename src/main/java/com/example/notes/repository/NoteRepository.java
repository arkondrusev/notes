package com.example.notes.repository;

import com.example.notes.model.Note;
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
public class NoteRepository {

    private SessionFactory sessionFactory;

    public Optional<Note> findById(Integer id) {
        return sessionFactory.getCurrentSession()
                .createQuery("select n from Note n where n.id = :id", Note.class)
                .setParameter("id", id).stream().findFirst();
    }

    public Optional<Note> findByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("select n from Note n where n.name = :name", Note.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }

    public Optional<Note> findByTopicId(Integer topicId) {
        return sessionFactory.getCurrentSession()
                .createQuery("select n from Note n where n.topic.id = :topicId", Note.class)
                .setParameter("topicId", topicId)
                .getResultList().stream().findFirst();
    }

    public Set<Note> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("select n from Note n", Note.class)
                .getResultStream().collect(Collectors.toSet());
    }

    public Note create(Note newNote) {
        sessionFactory.getCurrentSession().persist(newNote);
        return newNote;
    }

    public boolean update(Note newNote) {
        Session session = sessionFactory.getCurrentSession();
        Note storedNote = session.get(Note.class, newNote.getId(), LockMode.PESSIMISTIC_READ);
        if (storedNote == null) {
            return false;
        }
        session.merge(newNote);
        return true;
    }

    public boolean delete(Integer noteId) {
        Session session = sessionFactory.getCurrentSession();
        Note noteForDelete = session.get(Note.class, noteId);
        if (noteForDelete == null) {
            return false;
        }
        session.remove(noteForDelete);
        return true;
    }

}
