package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
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
        Note newNote = new Note(noteIdSequence.incrementAndGet(), request.getNoteName(),
                topicOpt.get(), request.getNoteContent(), findTagsByTagWrappers(request.getNoteTagList()));
        noteList.add(newNote);

        return new CreateNoteResponse(newNote.getId(), newNote.getName());
    }

    private Note findNoteById(int id) {
        return noteList.stream().filter(note -> note.getId() == id).findFirst().orElse(null);
    }

    private Set<Tag> findTagsByTagWrappers(Set<TagWrapper> tagWrapperList) {
        Set<Tag> tagList = new HashSet<>();
        tagWrapperList.forEach(tw->{
            Optional<Tag> tag = tagService.findTagById(tw.getTagId());
            if (tag.isEmpty()) {
                throw new RuntimeException(String.format("Tag with id={0} not found",tw.getTagId()));
            }
            tagList.add(tag.get());
        });

        return tagList;
    }

    public OperationResponse updateNote(UpdateNoteRequest request) {
        //todo check request params
        Note foundNote = findNoteById(request.getNoteId());
        if (foundNote == null) {
            throw new RuntimeException(String.format("Note with id={0} not found",request.getNoteId()));
        }


        if (request.getTopicId() != null) {
            Optional<Topic> topicOpt = topicService.findTopicById(request.getTopicId());
            if (topicOpt.isEmpty()) {
                throw new RuntimeException(String.format("Topic with id={0} not found",request.getTopicId()));
            } else {
                foundNote.setTopic(topicOpt.get());
            }
        }
        foundNote.setTagList(findTagsByTagWrappers(request.getNoteTagList()));
        foundNote.setContent(request.getNoteContent());
        foundNote.setName(request.getNoteName());

        return OperationResponse.ok();
    }

    public OperationResponse deleteNote(DeleteNoteRequest request) {
        return OperationResponse.ok();
    }

    public GetNoteListResponse getNoteList() {
        Set<NoteWrapper> noteWrapperList = new HashSet<>();
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
            noteWrapperList.add(new NoteWrapper(note.getId(), note.getName(), topicId, topicName, note.getContent(), tagWrapperList));
        });
        return new GetNoteListResponse(noteWrapperList);
    }

}
