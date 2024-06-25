package com.example.notes.mapper;

import com.example.notes.dto.tag.CreateTagResponse;
import com.example.notes.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = NotesAppMapperConfig.class)
public interface Tag2CreateTagResponseMapper {

    Tag2CreateTagResponseMapper INSTANCE = Mappers.getMapper(Tag2CreateTagResponseMapper.class);

    @Mapping(source = "id", target = "tagId")
    @Mapping(source = "name", target = "tagName")
    @Mapping(target = "resultCode", ignore = true)
    @Mapping(target = "resultMessage", ignore = true)
    CreateTagResponse tag2CreateTagResponse(Tag tag);

}
