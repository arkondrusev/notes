package com.example.notes.dto.topic;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class GetTopicTreeResponse {

    private Set<TopicWrapper> rootTopicList;

}
