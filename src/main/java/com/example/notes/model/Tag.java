package com.example.notes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tag {

    private Integer id;
    private String name;

    public boolean equals(Object object) {
        boolean result = false;
        if (object instanceof Tag) {
            result = id == ((Tag) object).id;
        }

        return result;
    }

}
