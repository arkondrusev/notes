package com.example.notes.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"topic"})
@Builder(setterPrefix = "set")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="note_id_seq")
    @SequenceGenerator(name="note_id_seq", sequenceName="note_id_seq", allocationSize=1)
    @Column(name = "id", updatable=false)
    @EqualsAndHashCode.Include
    private Integer id;
    @NonNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    private String content;
    @ManyToMany
    @JoinTable(name = "note_to_tag",
            joinColumns = @JoinColumn(name = "note_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tagList;

    public Note(Integer id, String name, Topic parentTopic, String content) {
        this(id, name, parentTopic, content, new HashSet<>());
    }

    public Note(Integer id, String name, Topic parentTopic) {
        this(id, name, parentTopic, "");
    }

    public Set<Tag> getTagList() {
        if (tagList == null) {
            tagList = new HashSet<>();
        }
        return tagList;
    }

}
