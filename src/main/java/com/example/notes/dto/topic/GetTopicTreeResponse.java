package com.example.notes.dto.topic;

import com.example.notes.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class GetTopicTreeResponse {

    private Set<Topic> rootTopicList = new HashSet<>();

}
