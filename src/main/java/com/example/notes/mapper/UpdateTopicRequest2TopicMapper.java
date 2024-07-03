package com.example.notes.mapper;

import com.example.notes.dto.topic.UpdateTopicRequest;
import com.example.notes.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = NotesAppMapperConfig.class)
public interface UpdateTopicRequest2TopicMapper {

    UpdateTopicRequest2TopicMapper INSTANCE = Mappers.getMapper(UpdateTopicRequest2TopicMapper.class);

    @Mapping(target = "id", source = "request.topicId")
    @Mapping(target = "name", source = "request.topicName")
    @Mapping(target = "parentTopic", expression = "java(parentTopic)")
    Topic UpdateTopicRequest2Topic(UpdateTopicRequest request, Topic parentTopic);

}
