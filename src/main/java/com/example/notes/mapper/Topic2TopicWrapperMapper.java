package com.example.notes.mapper;

import com.example.notes.dto.topic.TopicWrapper;
import com.example.notes.model.Topic;
import com.example.notes.repository.TopicRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Mapper(config = NotesAppMapperConfig.class, componentModel="spring")
public abstract class Topic2TopicWrapperMapper {

    public static final Topic2TopicWrapperMapper INSTANCE = Mappers.getMapper(Topic2TopicWrapperMapper.class);

    @Autowired
    protected TopicRepository topicRepository;

    @Mapping(source = "id", target = "topicId")
    @Mapping(source = "name", target = "topicName")
    @Mapping(source = "parentTopic.id", target = "parentTopicId")
    @Mapping(target = "childrenTopicList", expression = "java(getChildrenTopicWrapperList(topic))")
    public abstract TopicWrapper topic2TopicWrapper(Topic topic);

    protected Set<TopicWrapper> getChildrenTopicWrapperList(Topic topic) {
        Set<TopicWrapper> childrenTopicList = new HashSet<>();
        topicRepository.findListByParentId(topic.getId())
                .forEach(child -> childrenTopicList.add(topic2TopicWrapper(child)));
        return childrenTopicList;
    }

}
