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
import java.util.Optional;
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

    public OperationResponse createNote(@NonNull CreateNoteRequest request) {
        Note newNote;
        try {
            checkCreateNoteRequestParams(request);

            Set<Tag> tagList = getTagListByTagWrapperListAndCheck(request.getNoteTagList());

            newNote = noteRepository.save(new Note(null, request.getNoteName(),
                    findTopicOrThrow(request.getTopicId()), request.getNoteContent(), tagList));
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }
        return Note2CreateNoteResponseMapper.INSTANCE.note2CreateNoteResponse(newNote);
    }

    public OperationResponse updateNote(@NonNull UpdateNoteRequest request) {
        try {
            checkUpdateNoteRequestParams(request);

            Set<Tag> tagList = getTagListByTagWrapperListAndCheck(request.getNoteTagList());

            if (noteRepository.findById(request.getNoteId()).isEmpty()) {
                throw new RuntimeException(String.format(NOTE_NOT_FOUND_MESSAGE, request.getNoteId()));
            }

            noteRepository.save(new Note(request.getNoteId(), request.getNoteName(),
                    findTopicOrThrow(request.getTopicId()), request.getNoteContent(), tagList));
        } catch (Throwable t) {
            OperationResponse.error(t.getMessage());
        }
        return OperationResponse.ok();
    }

    public OperationResponse deleteNote(@NonNull DeleteNoteRequest request) {
        try {
            checkDeleteNoteRequestParams(request);

            Optional<Note> noteOpt = noteRepository.findById(request.getNoteId());
            if (noteOpt.isEmpty()) {
                throw new RuntimeException(String.format(NOTE_NOT_FOUND_MESSAGE, request.getNoteId()));
            }

            noteRepository.delete(noteOpt.get());
        } catch (Throwable t) {
            return OperationResponse.error(t.getMessage());
        }
        return OperationResponse.ok();
    }

    public GetNoteListResponse getNoteList() {
        Set<NoteWrapper> noteWrapperList = new HashSet<>();
        noteRepository.findAll().forEach(note ->
            noteWrapperList.add(Note2NoteWrapperMapper.INSTANCE.note2NoteWrapperMapper(note))
        );

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

    private Set<Tag> getTagListByTagWrapperListAndCheck(Set<TagWrapper> tagWrapperList) {
        Set<Tag> tagList = new HashSet<>();
        if (!tagWrapperList.isEmpty()) {
            Set<Integer> requestTagIdList = getTagIdListByTagWrapperList(tagWrapperList);
            tagList = new HashSet<>(tagRepository.findAllById(requestTagIdList));
            checkTagListFound(requestTagIdList, tagList);
        }
        return tagList;
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

}
