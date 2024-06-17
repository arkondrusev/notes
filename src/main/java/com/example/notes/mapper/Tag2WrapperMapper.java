package com.example.notes.mapper;

import com.example.notes.dto.tag.TagWrapper;
import com.example.notes.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface Tag2WrapperMapper {

    Tag2WrapperMapper INSTANCE = Mappers.getMapper(Tag2WrapperMapper.class);

    @Mapping(source = "id", target = "tagId")
    @Mapping(source = "name", target = "tagName")
    TagWrapper tag2TagWrapper(Tag tag);

    @Mapping(source = "tagId", target = "id")
    @Mapping(source = "tagName", target = "name")
    Tag tagWrapper2Tag(TagWrapper tagWrapper);

}
