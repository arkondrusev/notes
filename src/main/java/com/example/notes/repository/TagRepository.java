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

    private final Set<Tag> list = new HashSet<>();
    private final AtomicInteger idSequence = new AtomicInteger(0);

    public Optional<Tag> findById(Integer id) {
        HashSet<Integer> set = new HashSet<>();
        set.add(id);
        return findListByIdList(set).stream().findFirst();
    }

    public Set<Tag> findListByIdList(Set<Integer> idList) {
        return list.stream().filter((n)->idList.contains(n.getId())).collect(Collectors.toSet());
    }

    public Optional<Tag> findByName(String name) {
        return list.stream().filter(n -> n.getName().equals(name)).findFirst();
    }

    public Set<Tag> findAll() {
        return list;
    }

    public Tag create(String name) {
        Tag tag = new Tag(idSequence.incrementAndGet(), name);
        list.add(tag);

        return tag;
    }

    public void delete(Tag tag) {
        list.remove(tag);
    }

}
