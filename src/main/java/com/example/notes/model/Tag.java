package com.example.notes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tag {

    private Integer id;
    private String name;

    //todo custom "equals" method - compare only by "id"

}
