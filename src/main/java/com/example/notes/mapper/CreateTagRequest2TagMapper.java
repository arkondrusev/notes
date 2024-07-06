package com.example.notes.mapper;

import com.example.notes.dto.tag.CreateTagRequest;
import com.example.notes.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = NotesAppMapperConfig.class)
public interface CreateTagRequest2TagMapper {

    CreateTagRequest2TagMapper INSTANCE = Mappers.getMapper(CreateTagRequest2TagMapper.class);

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "name", source = "tagName")
    Tag CreateTagRequest2Tag(CreateTagRequest request);

}
