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

    private final Set<Note> noteSet = new HashSet<>();
    private final AtomicInteger noteIdSequence = new AtomicInteger(0);

    public Optional<Note> findNoteById(Integer id) {
        return noteSet.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    public Optional<Note> findNoteByName(String noteName) {
        return noteSet.stream().filter(n -> n.getName().equals(noteName)).findFirst();
    }

    private Optional<Note> findNoteByTopicId(Integer topicId) {
        return noteSet.stream().filter(note -> note.getTopic().getId() == topicId).findFirst();
    }

    public Set<Note> findAllNotes() {
        return noteSet;
    }

    public Note createNote(String noteName, Topic topic, String noteContent, Set<Tag> noteTagList) {
        Note note = new Note(noteIdSequence.incrementAndGet(), noteName, topic, noteContent, noteTagList);
        noteSet.add(note);

        return note;
    }

    public void deleteNote(Note note) {
        noteSet.remove(note);
    }

}
