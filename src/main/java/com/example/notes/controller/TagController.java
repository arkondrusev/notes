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

    @PostMapping("createTag")
    public OperationResponse createTag(@RequestBody CreateTagRequest request) {
        return tagService.createTag(request);
    }

    @GetMapping("getTagList")
    public GetTagListResponse getTagList() {
        return tagService.getTagList();
    }

    @PutMapping("updateTag")
    public OperationResponse updateTag(@RequestBody UpdateTagRequest request) {
        return tagService.updateTag(request);
    }

    @DeleteMapping("deleteTag")
    public OperationResponse deleteTag(@RequestBody DeleteTagRequest request) {
        return tagService.deleteTag(request);
    }

}

