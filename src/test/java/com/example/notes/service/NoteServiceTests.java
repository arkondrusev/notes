package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.note.*;
import com.example.notes.dto.tag.TagWrapper;
import com.example.notes.model.Note;
import com.example.notes.model.Tag;
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
//        when(noteRepository.findAll()).thenReturn(noteList);

        GetNoteListResponse actualResponse = noteService.getNoteList();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createNote_success() {
        Integer noteId = 1;
        Set<Tag> tagList = new HashSet<>();
        tagList.add(new Tag(1, "Test Tag 1"));
        Set<TagWrapper> tagWrapperList = new HashSet<>();
        tagWrapperList.add(new TagWrapper(1, "Test Tag 1"));
        Topic topic1 = new Topic(1, "Test Topic 1");
        Note note1 = new Note(noteId, "Test Topic 1",
                topic1, "Test Note Content 1", tagList);
        CreateNoteRequest request = new CreateNoteRequest(note1.getName(),
                note1.getTopic().getId(), note1.getContent(), tagWrapperList);
//        when(tagRepository.findListByIdList(any())).thenReturn(tagList);
        when(topicRepository.findById(topic1.getId())).thenReturn(Optional.of(topic1));
//        when(noteRepository.create(new Note(null, "Test Topic 1",
//                topic1, "Test Note Content 1", tagList))).thenReturn(note1);

        assertEquals(new CreateNoteResponse(note1.getId(), note1.getName()), noteService.createNote(request));
    }

    @Test
    void updateNote_success() {
        Integer noteId = 1;
        Set<Tag> tagList = new HashSet<>();
        tagList.add(new Tag(1, "Test Tag 1"));
        Set<TagWrapper> tagWrapperList = new HashSet<>();
        tagWrapperList.add(new TagWrapper(1, "Test Tag 1"));
        Topic topic1 = new Topic(1, "Test Topic 1");
        Note note1 = new Note(noteId, "Test Topic 1",
                topic1, "Test Note Content 1", tagList);
        UpdateNoteRequest request = new UpdateNoteRequest(note1.getId(), note1.getName(),
                note1.getTopic().getId(), note1.getContent(), tagWrapperList);
//        when(tagRepository.findListByIdList(any())).thenReturn(tagList);
        when(topicRepository.findById(topic1.getId())).thenReturn(Optional.of(topic1));
//        when(noteRepository.update(note1)).thenReturn(Boolean.TRUE);

        assertEquals(OperationResponse.ok(), noteService.updateNote(request));
    }

    @Test
    void deleteNote_success() {
        Integer noteId = 1;
//        when(noteRepository.delete(noteId)).thenReturn(Boolean.TRUE);

        assertEquals(OperationResponse.ok(), noteService.deleteNote(new DeleteNoteRequest(noteId)));
    }

}
