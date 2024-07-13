package com.example.notes.mapper;

import com.example.notes.dto.tag.UpdateTagRequest;
import com.example.notes.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = NotesAppMapperConfig.class)
public interface UpdateTagRequest2TagMapper {

    UpdateTagRequest2TagMapper INSTANCE = Mappers.getMapper(UpdateTagRequest2TagMapper.class);

    @Mapping(target = "id", source = "tagId")
    @Mapping(target = "name", source = "tagName")
    Tag UpdateTagRequest2Tag(UpdateTagRequest request);

}
