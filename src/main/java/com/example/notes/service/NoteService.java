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
import java.util.Set;

import static com.example.notes.service.TopicService.TOPIC_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class NoteService {

    public static final String NOTE_NOT_FOUND_MESSAGE = "Note not found: id=%s";
    public static final String TAGS_NOT_FOUND_MESSAGE = "Tags not found: id=%s";

    public static final String NOTE_NAME_IS_EMPTY_MESSAGE = "Note name is empty";
    public static final String PARENT_TOPIC_ID_IS_EMPTY_MESSAGE = "Parent topic id is empty";
    public static final String NOTE_ID_IS_EMPTY_MESSAGE = "Note id is empty";

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

    public void checkCreateNoteRequestParams(@NonNull CreateNoteRequest request) {
        if (request.getNoteName() == null || request.getNoteName().isEmpty()) {
            throw new IllegalArgumentException(NOTE_NAME_IS_EMPTY_MESSAGE);
        }
        if (request.getTopicId() == null) {
            throw new IllegalArgumentException(PARENT_TOPIC_ID_IS_EMPTY_MESSAGE);
        }
    }

    public void checkUpdateNoteRequestParams(@NonNull UpdateNoteRequest request) {
        if (request.getNoteId() == null) {
            throw new IllegalArgumentException(NOTE_ID_IS_EMPTY_MESSAGE);
        }
        if (request.getNoteName() == null || request.getNoteName().isEmpty()) {
            throw new IllegalArgumentException(NOTE_NAME_IS_EMPTY_MESSAGE);
        }
        if (request.getTopicId() == null) {
            throw new IllegalArgumentException(PARENT_TOPIC_ID_IS_EMPTY_MESSAGE);
        }
    }

    public void checkDeleteNoteRequestParams(@NonNull DeleteNoteRequest request) {
        if (request.getNoteId() == null) {
            throw new IllegalArgumentException(NOTE_ID_IS_EMPTY_MESSAGE);
        }
    }

    public OperationResponse createNote(@NonNull CreateNoteRequest request) {
        Note newNote;
        try {
            checkCreateNoteRequestParams(request);
            Set<Tag> tagList = new HashSet<>();
            if (!request.getNoteTagList().isEmpty()) {
                Set<Integer> requestTagIdList = getTagIdListByTagWrapperList(request.getNoteTagList());
                tagList = tagRepository.findListByIdList(requestTagIdList);
                checkTagListFound(requestTagIdList, tagList);
            }
            newNote = new Note(null, request.getNoteName(), findTopicOrThrow(request.getTopicId()),
                    request.getNoteContent(), tagList);
            newNote = noteRepository.create(newNote);
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }
        return Note2CreateNoteResponseMapper.INSTANCE.note2CreateNoteResponse(newNote);
    }

    private void checkTagListFound(Set<Integer> tagIdList, Set<Tag> tagList) {
        Set<Integer> tagIdNotFoundList = new HashSet<>(tagIdList);
        tagList.forEach(t->tagIdNotFoundList.remove(t.getId()));
        if (!tagIdNotFoundList.isEmpty()) {
            throw new RuntimeException(String.format(TAGS_NOT_FOUND_MESSAGE, tagIdNotFoundList));
        }
    }

    private @NonNull Set<Integer> getTagIdListByTagWrapperList(@NonNull Set<TagWrapper> tagWrapperList) {
        Set<Integer> tagIdList = new HashSet<>();
        tagWrapperList.forEach(tagWrapper -> tagIdList.add(tagWrapper.getTagId()));
        return tagIdList;
    }

    private Topic findTopicOrThrow(Integer topicId) {
        return topicRepository.findById(topicId).orElseThrow(() -> new RuntimeException(
                        String.format(TOPIC_NOT_FOUND_MESSAGE, topicId)));
    }

    public OperationResponse updateNote(@NonNull UpdateNoteRequest request) {
        try {
            checkUpdateNoteRequestParams(request);
            Set<Tag> tagList = new HashSet<>();
            if (!request.getNoteTagList().isEmpty()) {
                Set<Integer> requestTagIdList = getTagIdListByTagWrapperList(request.getNoteTagList());
                tagList = tagRepository.findListByIdList(requestTagIdList);
                checkTagListFound(requestTagIdList, tagList);
            }
            if (!noteRepository.update(new Note(request.getNoteId(), request.getNoteName(),
                    findTopicOrThrow(request.getTopicId()), request.getNoteContent(), tagList))) {
                throw new RuntimeException(String.format(NOTE_NOT_FOUND_MESSAGE, request.getNoteId()));
            }
        } catch (Throwable t) {
            OperationResponse.error(t.getMessage());
        }
        return OperationResponse.ok();
    }

    public OperationResponse deleteNote(@NonNull DeleteNoteRequest request) {
        try {
            checkDeleteNoteRequestParams(request);
            if (!noteRepository.delete(request.getNoteId())) {
                throw new RuntimeException(String.format(NOTE_NOT_FOUND_MESSAGE, request.getNoteId()));
            }
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }
        return OperationResponse.ok();
    }

}
