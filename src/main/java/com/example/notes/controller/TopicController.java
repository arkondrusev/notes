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

    @PostMapping("create")
    public OperationResponse createTopic(@RequestBody CreateTopicRequest request) {
        return topicService.createTopic(request);
    }

    //todo implement possibility to get subtree using "topicId" as parameter
    @GetMapping("get-tree")
    public OperationResponse getTopicTree() {
        return topicService.getTopicTree();
    }

    @PutMapping("update")
    public OperationResponse updateTopic(@RequestBody UpdateTopicRequest request) {
        return topicService.updateTopic(request);
    }

    @DeleteMapping("delete")
    public OperationResponse deleteTopic(@RequestBody DeleteTopicRequest request) {
        return topicService.deleteTopic(request);
    }

}
