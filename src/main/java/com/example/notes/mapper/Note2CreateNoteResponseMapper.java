package com.example.notes.mapper;

import com.example.notes.dto.note.CreateNoteResponse;
import com.example.notes.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = NotesAppMapperConfig.class)
public interface Note2CreateNoteResponseMapper {

    Note2CreateNoteResponseMapper INSTANCE = Mappers.getMapper(Note2CreateNoteResponseMapper.class);

    @Mapping(source = "id", target = "noteId")
    @Mapping(source = "name", target = "noteName")
    @Mapping(target = "resultCode", ignore = true)
    @Mapping(target = "resultMessage", ignore = true)
    CreateNoteResponse note2CreateNoteResponse(Note note);

}
