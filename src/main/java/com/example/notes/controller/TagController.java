package com.example.notes.controller;

import com.example.notes.dto.CreateTagResponse;
import com.example.notes.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("tag")
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("createtag")
    public CreateTagResponse createTag(@RequestBody String name) {
        return tagService.addTag(name);
    }

}

