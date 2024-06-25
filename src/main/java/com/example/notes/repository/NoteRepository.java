package com.example.notes.repository;

import com.example.notes.model.Note;
import com.example.notes.model.Tag;
import com.example.notes.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class NoteRepository {

    private final Set<Note> list = new HashSet<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public Optional<Note> findById(Integer id) {
        return list.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    public Optional<Note> findByName(String name) {
        return list.stream().filter(n -> n.getName().equals(name)).findFirst();
    }

    public Optional<Note> findByTopicId(Integer topicId) {
        return list.stream().filter(note -> note.getTopic().getId().equals(topicId)).findFirst();
    }

    public Set<Note> findAll() {
        return list;
    }

    public Note create(String name, Topic topic, String content, Set<Tag> tagList) {
        Note note = new Note(idSequence.incrementAndGet(), name, topic, content, tagList);
        list.add(note);

        return note;
    }

    public void delete(Note note) {
        list.remove(note);
    }

}
