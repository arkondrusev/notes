package com.example.notes.mapper;

import com.example.notes.dto.topic.TopicWrapper;
import com.example.notes.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(config = NotesAppMapperConfig.class, componentModel="spring")
public interface Topic2TopicWrapperMapper {

    Topic2TopicWrapperMapper INSTANCE = Mappers.getMapper(Topic2TopicWrapperMapper.class);

    @Mapping(target = "topicId", source = "topic.id")
    @Mapping(target = "topicName", source = "topic.name")
    @Mapping(target = "parentTopicId", source = "topic.parentTopic.id")
    @Mapping(target = "childrenTopicList", expression = "java(childrenTopicList)")
    TopicWrapper topic2TopicWrapper(Topic topic, Set<TopicWrapper> childrenTopicList);

}
