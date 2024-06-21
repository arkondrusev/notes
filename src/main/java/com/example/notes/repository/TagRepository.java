package com.example.notes.repository;

import com.example.notes.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class TagRepository {

    private final Set<Tag> tagSet = new HashSet<>();
    private final AtomicInteger tagIdSequence = new AtomicInteger(0);

    public Optional<Tag> findTagById(Integer id) {
        HashSet<Integer> set = new HashSet<>();
        set.add(id);
        return findTagListByIdList(set).stream().findFirst();
    }

    public Set<Tag> findTagListByIdList(Set<Integer> idList) {
        return tagSet.stream().filter((n)->idList.contains(n.getId())).collect(Collectors.toSet());
    }

    public Optional<Tag> findTagByName(String tagName) {
        return tagSet.stream().filter(n -> n.getName().equals(tagName)).findFirst();
    }

    public Set<Tag> findAllTags() {
        return tagSet;
    }

    public Tag createTag(String tagName) {
        Tag tag = new Tag(tagIdSequence.incrementAndGet(), tagName);
        tagSet.add(tag);

        return tag;
    }

    public void deleteTag(Tag tag) {
        tagSet.remove(tag);
    }

}
