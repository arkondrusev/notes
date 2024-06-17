package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.note.*;
import com.example.notes.dto.tag.TagWrapper;
import com.example.notes.mapper.Tag2WrapperMapper;
import com.example.notes.model.Note;
import com.example.notes.model.Tag;
import com.example.notes.model.Topic;
import com.example.notes.repository.NoteRepository;
import com.example.notes.repository.TagRepository;
import com.example.notes.repository.TopicRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.example.notes.service.TopicService.TOPIC_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class NoteService {

    public static final String NOTE_NOT_FOUND_MESSAGE = "Note not found: id=%s";

    private final NoteRepository noteRepository;
    private final TopicRepository topicRepository;
    private final TagRepository tagRepository;

    public GetNoteListResponse getNoteList() {
        Set<NoteWrapper> noteWrapperList = new HashSet<>();
        noteRepository.findAllNotes().forEach(note -> {
            Integer topicId = note.getTopic().getId();
            String topicName = note.getTopic().getName();
            Set<TagWrapper> tagWrapperList = new HashSet<>();
            note.getTagList().forEach(tag -> tagWrapperList.add(Tag2WrapperMapper.INSTANCE.tag2TagWrapper(tag)));
            noteWrapperList.add(new NoteWrapper(note.getId(), note.getName(), topicId, topicName, note.getContent(), tagWrapperList));
        });

        return new GetNoteListResponse(noteWrapperList);
    }

    public CreateNoteResponse createNote(@NonNull CreateNoteRequest request) {
        //todo check request params (name, topicId) exist

        Topic topic = topicRepository.findTopicById(request.getTopicId())
                .orElseThrow(() -> new RuntimeException(
                        String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId())));
        Set<Tag> tagListByIdList = tagRepository
                .findTagListByIdList(getTagIdListByTagWrapperList(request.getNoteTagList()));
        //todo check if found a Tag for every id in list, throw exception otherwise
        Note newNote = noteRepository.createNote(request.getNoteName(),
                topic, request.getNoteContent(), tagListByIdList);

        return new CreateNoteResponse(newNote.getId(), newNote.getName());
    }

    private @NonNull Set<Integer> getTagIdListByTagWrapperList(@NonNull Set<TagWrapper> tagWrapperList) {
        Set<Integer> tagIdList = new HashSet<>();
        tagWrapperList.forEach(tagWrapper -> tagIdList.add(tagWrapper.getTagId()));

        return tagIdList;
    }

    public OperationResponse updateNote(@NonNull UpdateNoteRequest request) {
        //todo check request params

        Note storedNote = noteRepository.findNoteById(request.getNoteId())
                .orElseThrow(() -> new RuntimeException(
                        String.format(NOTE_NOT_FOUND_MESSAGE, request.getNoteId())));
        //update note's topic
        if (!(Objects.equals(request.getTopicId(), storedNote.getTopic() == null
                ? null : storedNote.getTopic().getId()))) {
            if (Objects.nonNull(request.getTopicId())) {
                storedNote.setTopic(topicRepository.findTopicById(request.getTopicId())
                        .orElseThrow(() -> new RuntimeException(
                                String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId()))));
            } else {
                storedNote.setTopic(null);
            }
        }
        Set<Tag> tagListByIdList = tagRepository
                .findTagListByIdList(getTagIdListByTagWrapperList(request.getNoteTagList()));
        //todo check if found a Tag for every id in list, throw exception otherwise
        storedNote.setTagList(tagListByIdList);
        storedNote.setContent(request.getNoteContent());
        storedNote.setName(request.getNoteName());

        return OperationResponse.ok();
    }

    public OperationResponse deleteNote(@NonNull DeleteNoteRequest request) {
        // todo check request params
        noteRepository.deleteNote(noteRepository.findNoteById(request.getNoteId())
                .orElseThrow(()-> new RuntimeException(String.format(NOTE_NOT_FOUND_MESSAGE, request.getNoteId()))));

        return OperationResponse.ok();
    }

}
