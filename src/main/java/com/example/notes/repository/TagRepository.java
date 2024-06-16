package com.example.notes.repository;

import com.example.notes.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TagRepository {

    private final Set<Tag> tagSet = new HashSet<>();
    private final AtomicInteger tagIdSequence = new AtomicInteger(0);

    public Optional<Tag> findTagById(Integer id) {
        return tagSet.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    public Optional<Tag> findTagByName(String tagName) {
        return tagSet.stream().filter(n -> n.getName().equals(tagName)).findFirst();
    }

    public Set<Tag> findAllTags() {
        return tagSet;
    }

    public Tag addTag(String tagName) {
        Tag tag = new Tag(tagIdSequence.incrementAndGet(), tagName);
        tagSet.add(tag);

        return tag;
    }

    public void deleteTag(Tag tag) {
        tagSet.remove(tag);
    }

}
