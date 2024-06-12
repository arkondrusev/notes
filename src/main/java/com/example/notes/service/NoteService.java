package com.example.notes.service;

import com.example.notes.dto.note.*;
import com.example.notes.dto.tag.TagWrapper;
import com.example.notes.model.Note;
import com.example.notes.model.Tag;
import com.example.notes.model.Topic;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NoteService {

    private final Set<Note> noteList = new HashSet<>();
    private final AtomicInteger noteIdSequence = new AtomicInteger(0);
    private final TopicService topicService;
    private final TagService tagService;

    public NoteService(TopicService topicService, TagService tagService) {
        this.topicService = topicService;
        this.tagService = tagService;
    }

    public CreateNoteResponse addNote(CreateNoteRequest request) {
        //todo check request params (name, topicId) exist
        Optional<Topic> topicOpt = topicService.findTopicById(request.getTopicId());
        if (topicOpt.isEmpty()) {
            throw new RuntimeException(String.format("Topic with id={0} not found",request.getTopicId()));
        }
        Set<Tag> tagList = new HashSet<>();
        request.getNoteTagList().forEach(tw->{
            Optional<Tag> tag = tagService.findTagById(tw.getTagId());
            if (tag.isEmpty()) {
                throw new RuntimeException(String.format("Tag with id={0} not found",tw.getTagId()));
            }
            tagList.add(tag.get());
        });
        Note newNote = new Note(noteIdSequence.incrementAndGet(), request.getNoteName(),
                topicOpt.get(), request.getNoteContent(), tagList);
        noteList.add(newNote);

        return new CreateNoteResponse(newNote.getId(), newNote.getName());
    }

    public UpdateNoteResponse updateNote(UpdateNoteRequest request) {
        return new UpdateNoteResponse();
    }

    public DeleteNoteResponse deleteNote(DeleteNoteRequest request) {
        return new DeleteNoteResponse();
    }

    public GetNoteListResponse getNoteList() {
        GetNoteListResponse response = new GetNoteListResponse();
        noteList.forEach(note -> {
            Integer topicId = null;
            String topicName = null;
            if (note.getTopic() != null) {
                topicId = note.getTopic().getId();
                topicName = note.getTopic().getName();
            }
            Set<TagWrapper> tagWrapperList = new HashSet<>();
            note.getTagList().forEach(tag -> {
                tagWrapperList.add(new TagWrapper(tag.getId(), tag.getName()));
            });
            response.getNoteList().add(new NoteWrapper(note.getId(), note.getName(), topicId, topicName, note.getContent(), tagWrapperList));
        });
        return response;
    }

}
