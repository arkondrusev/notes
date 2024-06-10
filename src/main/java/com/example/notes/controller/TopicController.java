package com.example.notes.controller;

import com.example.notes.dto.topic.CreateTopicRequest;
import com.example.notes.dto.topic.CreateTopicResponse;
import com.example.notes.dto.topic.GetTopicTreeResponse;
import com.example.notes.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("topic")
@RestController
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping("createTopic")
    public CreateTopicResponse createTopic(@RequestBody CreateTopicRequest request) {
        return topicService.addTopic(request);
    }

    //todo implement possibility to get subtree using "topicId" as parameter
    @GetMapping("getTopicTree")
    public GetTopicTreeResponse getTopicTree() {
        return topicService.getTopicTree();
    }

    @DeleteMapping("deleteTopic")
    public void deleteTopic(@RequestBody Integer topicId) {
        topicService.deleteTopic(topicId);
    }

}
