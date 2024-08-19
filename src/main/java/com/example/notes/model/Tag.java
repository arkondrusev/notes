package com.example.notes.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tag")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "set")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tag_id_seq")
    @SequenceGenerator(name="tag_id_seq", sequenceName="tag_id_seq", allocationSize=1)
    @Column(name = "id", updatable=false)
    @EqualsAndHashCode.Include
    private Integer id;
    @NonNull
    @Column(unique=true)
    private String name;

}
