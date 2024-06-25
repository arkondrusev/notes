package com.example.notes.mapper;

import com.example.notes.dto.tag.TagWrapper;
import com.example.notes.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(config = NotesAppMapperConfig.class)
public interface Tag2TagWrapperMapper {

    Tag2TagWrapperMapper INSTANCE = Mappers.getMapper(Tag2TagWrapperMapper.class);

    @Mapping(source = "id", target = "tagId")
    @Mapping(source = "name", target = "tagName")
    TagWrapper tag2TagWrapper(Tag tag);

    Set<TagWrapper> tag2TagWrapperList(Set<Tag> tagList);

}
