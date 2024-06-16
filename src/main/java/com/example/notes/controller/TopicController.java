package com.example.notes.controller;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.topic.*;
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
        return topicService.createTopic(request);
    }

    //todo implement possibility to get subtree using "topicId" as parameter
    @GetMapping("getTopicTree")
    public GetTopicTreeResponse getTopicTree() {
        return topicService.getTopicTree();
    }

    @PutMapping("updateTopic")
    public OperationResponse updateTopic(@RequestBody UpdateTopicRequest request) {
        return topicService.updateTopic(request);
    }

    @DeleteMapping("deleteTopic")
    public OperationResponse deleteTopic(@RequestBody DeleteTopicRequest request) {
        return topicService.deleteTopic(request);
    }

}
