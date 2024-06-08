package com.example.notes.controller;

import com.example.notes.dto.CreateTagResponse;
import com.example.notes.dto.GetTagListResponse;
import com.example.notes.model.Tag;
import com.example.notes.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("tag")
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("createTag")
    public CreateTagResponse createTag(@RequestBody String name) {
        return tagService.addTag(name);
    }

    @GetMapping("getTagList")
    public GetTagListResponse getTagList() {
        return tagService.getTagList();
    }

    @PutMapping("updateTag")
    public void updateTag(@RequestBody Tag updatedTag) {
        tagService.updateTag(updatedTag);
    }

    @DeleteMapping("deleteTag")
    public void deleteTag(@RequestBody Integer id) {
        tagService.deleteTag(id);
    }

}

