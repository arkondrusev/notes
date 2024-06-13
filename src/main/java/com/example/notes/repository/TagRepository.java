package com.example.notes.repository;

import com.example.notes.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TagRepository {

    private final Set<Tag> tagSet = new HashSet<>();
    private final AtomicInteger tagIdSequence = new AtomicInteger(0);

    public Tag addTag(String tagName) {
        Tag tag = new Tag(tagIdSequence.incrementAndGet(), tagName);
        tagSet.add(tag);

        return tag;
    }

}
