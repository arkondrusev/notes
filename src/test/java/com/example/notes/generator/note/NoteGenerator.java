package com.example.notes.generator.note;

import com.example.notes.model.Note;
import com.example.notes.model.Topic;

import static com.example.notes.generator.topic.TopicGenerator.*;

public class NoteGenerator {

    public static final Integer NOTE_ID = 1;
    public static final String NOTE_NAME = "NOTE 1";

    public static Note.NoteBuilder generateNoteAfterCreateBuilder() {
        Topic topic = generateTopicAfterCreateBuilder().setId(TOPIC_ID).setName(TOPIC_NAME).build();
        return Note.builder().setId(NOTE_ID).setName(NOTE_NAME).setTopic(topic);
    }

    public static Note.NoteBuilder generateNoteAfterUpdateBuilder(String newNameField) {
        return generateNoteAfterCreateBuilder().setName(newNameField);
    }

}
