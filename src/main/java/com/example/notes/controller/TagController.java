package com.example.notes.controller;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.tag.*;
import com.example.notes.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("tag")
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("create")
    public OperationResponse createTag(@RequestBody CreateTagRequest request) {
        return tagService.createTag(request);
    }

    @GetMapping("get-list")
    public GetTagListResponse getTagList() {
        return tagService.getTagList();
    }

    @PutMapping("update")
    public OperationResponse updateTag(@RequestBody UpdateTagRequest request) {
        return tagService.updateTag(request);
    }

    @DeleteMapping("delete")
    public OperationResponse deleteTag(@RequestBody DeleteTagRequest request) {
        return tagService.deleteTag(request);
    }

}

