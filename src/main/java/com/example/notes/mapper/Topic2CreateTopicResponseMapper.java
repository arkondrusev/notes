package com.example.notes.mapper;

import com.example.notes.dto.topic.CreateTopicResponse;
import com.example.notes.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(config = NotesAppMapperConfig.class)
public interface Topic2CreateTopicResponseMapper {

    Topic2CreateTopicResponseMapper INSTANCE = Mappers.getMapper(Topic2CreateTopicResponseMapper.class);

    @Mapping(source = "id", target = "topicId")
    @Mapping(source = "name", target = "topicName")
    @Mapping(source = "parentTopic.id", target = "parentTopicId")
    @Mapping(target = "resultCode", ignore = true)
    @Mapping(target = "resultMessage", ignore = true)
    CreateTopicResponse topic2CreateTopicResponse(Topic topic);

}
