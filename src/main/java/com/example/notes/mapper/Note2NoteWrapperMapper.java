package com.example.notes.mapper;

import com.example.notes.dto.note.NoteWrapper;
import com.example.notes.dto.tag.TagWrapper;
import com.example.notes.model.Note;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper(config = NotesAppMapperConfig.class)
public abstract class Note2NoteWrapperMapper {

    public static final Note2NoteWrapperMapper INSTANCE = Mappers.getMapper(Note2NoteWrapperMapper.class);

    @Mapping(source = "id", target = "noteId")
    @Mapping(source = "name", target = "noteName")
    @Mapping(source = "topic.id", target = "topicId")
    @Mapping(source = "topic.name", target = "topicName")
    @Mapping(source = "content", target = "noteContent")
    @Mapping(target = "noteTagList", expression = "java(getNoteTagList(note))")
    public abstract NoteWrapper note2NoteWrapperMapper(Note note);

    protected Set<TagWrapper> getNoteTagList(@NonNull Note note) {
        Set<TagWrapper> tagWrapperList = new HashSet<>();
        note.getTagList().forEach(tag -> tagWrapperList.add(Tag2TagWrapperMapper.INSTANCE.tag2TagWrapper(tag)));
        return tagWrapperList;
    }

}
