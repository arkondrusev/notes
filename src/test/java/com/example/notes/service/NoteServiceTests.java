package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.note.*;
import com.example.notes.model.Note;
import com.example.notes.model.Topic;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.TagRepository;
import com.example.notes.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        NoteService.class
})
public class NoteServiceTests {

    private final static String expectedNoteName1 = "Test Note 1";
    private final static String expectedNoteName2 = "Test Note 2";
    private final static String expectedNoteName3 = "Test Note 3";

    @Autowired
    private NoteService noteService;

    @MockBean
    private NoteRepository noteRepository;

    @MockBean
    private TopicRepository topicRepository;

    @MockBean
    private TagRepository tagRepository;

    @Test
    void getNoteList_success() {
        Topic topic1 = new Topic(1,"Test Topic 1");
        Topic topic2 = new Topic(2,"Test Topic 2");
        Set<NoteWrapper> noteWrapperList = new HashSet<>();
        noteWrapperList.add(new NoteWrapper(1, expectedNoteName1, topic1.getId()
                , topic1.getName(), "", new HashSet<>()));
        noteWrapperList.add(new NoteWrapper(2, expectedNoteName2, topic2.getId()
                , topic2.getName(), "", new HashSet<>()));
        GetNoteListResponse expectedResponse = new GetNoteListResponse(noteWrapperList);
        Set<Note> noteList = new HashSet<>();
        noteList.add(new Note(1, expectedNoteName1, topic1));
        noteList.add(new Note(2, expectedNoteName2, topic2));
        when(noteRepository.findAllNotes()).thenReturn(noteList);

        GetNoteListResponse actualResponse = noteService.getNoteList();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createNote_success() {
        Topic topic1 = new Topic(1,"Test Topic 1");
        Note note1 = new Note(1, expectedNoteName1, topic1);
        CreateNoteRequest request = new CreateNoteRequest(expectedNoteName1, topic1.getId());
        CreateNoteResponse expectedResponse = new CreateNoteResponse(1, expectedNoteName1);
        when(topicRepository.findTopicById(topic1.getId())).thenReturn(Optional.of(topic1));
        when(noteRepository.createNote(any(), any(), any(), any()))
                .thenReturn(note1);

        OperationResponse actualResponse = noteService.createNote(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateNote_success() {
        Topic topic1 = new Topic(1,"Test Topic 1");
        Note note1 = new Note(1, expectedNoteName1, topic1);
        UpdateNoteRequest request = new UpdateNoteRequest(note1.getId(), note1.getName(), note1.getTopic().getId());
        OperationResponse expectedResponse = OperationResponse.ok();
        when(noteRepository.findNoteById(note1.getId())).thenReturn(Optional.of(note1));

        OperationResponse actualResponse = noteService.updateNote(request);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void deleteNote_success() {
        Topic topic1 = new Topic(1,"Test Topic 1");
        Note note1 = new Note(1, expectedNoteName1, topic1);
        DeleteNoteRequest request = new DeleteNoteRequest(note1.getId());
        OperationResponse expectedResponse = OperationResponse.ok();
        when(noteRepository.findNoteById(note1.getId())).thenReturn(Optional.of(note1));

        OperationResponse actualResponse = noteService.deleteNote(request);

        assertEquals(expectedResponse, actualResponse);
    }

}
