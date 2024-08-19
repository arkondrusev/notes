package com.example.notes.generator.note;

import com.example.notes.dto.note.*;

import java.util.HashSet;
import java.util.Set;

import static com.example.notes.generator.topic.TopicGenerator.TOPIC_ID;
import static com.example.notes.generator.topic.TopicGenerator.TOPIC_NAME;

public class NoteDtoGenerator {

    public static final Integer NOTE_ID_1 = 1;
    public static final Integer NOTE_ID_2 = 2;
    public static final String NOTE_NAME_1 = "NOTE 1";
    public static final String NOTE_NAME_1_UPDATED = "NOTE 1 UPDATED";
    public static final String NOTE_NAME_2 = "NOTE 2";

    public static GetNoteListResponse.GetNoteListResponseBuilder generateGetNoteListResponseBuilder() {
        Set<NoteWrapper> NoteWrapperList = new HashSet<>();
        NoteWrapperList.add(NoteWrapper.builder()
                .setNoteId(NOTE_ID_1)
                .setNoteName(NOTE_NAME_1)
                .setTopicId(TOPIC_ID)
                .setTopicName(TOPIC_NAME).build());
        NoteWrapperList.add(NoteWrapper.builder()
                .setNoteId(NOTE_ID_2)
                .setNoteName(NOTE_NAME_2)
                .setTopicId(TOPIC_ID)
                .setTopicName(TOPIC_NAME).build());
        return GetNoteListResponse.builder().setNoteList(NoteWrapperList);
    }

    public static CreateNoteRequest.CreateNoteRequestBuilder generateCreateNoteRequestBuilder() {
        return CreateNoteRequest.builder().setNoteName(NOTE_NAME_1).setTopicId(TOPIC_ID);
    }

    public static CreateNoteResponse.CreateNoteResponseBuilder generateCreateNoteResponseBuilder() {
        return CreateNoteResponse.builder().setNoteId(NOTE_ID_1).setNoteName(NOTE_NAME_1)
                .setResultCode(0).setResultMessage("OK");
    }

    public static UpdateNoteRequest.UpdateNoteRequestBuilder generateUpdateNoteRequestBuilder() {
        return UpdateNoteRequest.builder()
                .setNoteId(NOTE_ID_1)
                .setNoteName(NOTE_NAME_1_UPDATED)
                .setTopicId(TOPIC_ID);
    }

    public static DeleteNoteRequest.DeleteNoteRequestBuilder generateDeleteNoteRequestBuilder() {
        return DeleteNoteRequest.builder().setNoteId(NOTE_ID_1);
    }

}
