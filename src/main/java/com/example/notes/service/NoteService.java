package com.example.notes.service;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.note.*;
import com.example.notes.dto.tag.TagWrapper;
import com.example.notes.mapper.Note2CreateNoteResponseMapper;
import com.example.notes.mapper.Note2NoteWrapperMapper;
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
        noteRepository.findAll().forEach(note -> {
            noteWrapperList.add(Note2NoteWrapperMapper.INSTANCE.note2NoteWrapperMapper(note));
        });

        return new GetNoteListResponse(noteWrapperList);
    }

    public OperationResponse createNote(@NonNull CreateNoteRequest request) {
        try {
            //todo check request params (name, topicId) exist

            Topic topic = topicRepository.findById(request.getTopicId())
                    .orElseThrow(() -> new RuntimeException(
                            String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId())));
            Set<Tag> tagListByIdList = tagRepository
                    .findListByIdList(getTagIdListByTagWrapperList(request.getNoteTagList()));
            //todo check if found a Tag for every id in list, throw exception otherwise
            Note newNote = noteRepository.create(request.getNoteName(),
                    topic, request.getNoteContent(), tagListByIdList);
            return Note2CreateNoteResponseMapper.INSTANCE.note2CreateNoteResponse(newNote);
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }
    }

    private @NonNull Set<Integer> getTagIdListByTagWrapperList(@NonNull Set<TagWrapper> tagWrapperList) {
        Set<Integer> tagIdList = new HashSet<>();
        tagWrapperList.forEach(tagWrapper -> tagIdList.add(tagWrapper.getTagId()));

        return tagIdList;
    }

    public OperationResponse updateNote(@NonNull UpdateNoteRequest request) {
        //todo check request params

        Note storedNote = noteRepository.findById(request.getNoteId())
                .orElseThrow(() -> new RuntimeException(
                        String.format(NOTE_NOT_FOUND_MESSAGE, request.getNoteId())));
        //update note's topic
        if (!(Objects.equals(request.getTopicId(), storedNote.getTopic() == null
                ? null : storedNote.getTopic().getId()))) {
            if (Objects.nonNull(request.getTopicId())) {
                storedNote.setTopic(topicRepository.findById(request.getTopicId())
                        .orElseThrow(() -> new RuntimeException(
                                String.format(TOPIC_NOT_FOUND_MESSAGE, request.getTopicId()))));
            } else {
                storedNote.setTopic(null);
            }
        }
        Set<Tag> tagListByIdList = tagRepository
                .findListByIdList(getTagIdListByTagWrapperList(request.getNoteTagList()));
        //todo check if found a Tag for every id in list, throw exception otherwise
        storedNote.setTagList(tagListByIdList);
        storedNote.setContent(request.getNoteContent());
        storedNote.setName(request.getNoteName());

        return OperationResponse.ok();
    }

    public OperationResponse deleteNote(@NonNull DeleteNoteRequest request) {
        try {
            // todo check request params
            noteRepository.delete(noteRepository.findById(request.getNoteId())
                    .orElseThrow(() -> new RuntimeException(String.format(NOTE_NOT_FOUND_MESSAGE, request.getNoteId()))));
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }
        return OperationResponse.ok();
    }

}
