package com.example.notes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "topic")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"parentTopic"})
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="topic_id_seq")
    @SequenceGenerator(name="topic_id_seq", sequenceName="topic_id_seq", allocationSize=1)
    @Column(name = "id", updatable=false)
    @EqualsAndHashCode.Include
    private Integer id;
    @NonNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Topic parentTopic;

    public Topic(Integer topicId, @NonNull String topicName, Topic parentTopic) {
        this.id = topicId;
        this.name = topicName;
        this.parentTopic = parentTopic;
    }

    public Topic(Integer topicId, @NonNull String topicName) {
        this(topicId, topicName, null);
    }

    //todo prohibit topic deletion if it has children entities (topics or notes) (PreRemove)

}
