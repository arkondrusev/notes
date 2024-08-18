package com.example.notes.generator.tag;

import com.example.notes.model.Tag;

public class TagGenerator {

    public static final Integer TAG_ID = 1;
    public static final String TAG_NAME = "TAG 1";

    public static Tag.TagBuilder generateTagAfterCreateBuilder() {
        return Tag.builder().setId(TAG_ID).setName(TAG_NAME);
    }

    public static Tag.TagBuilder generateTagAfterUpdateBuilder(String newNameField) {
        return generateTagAfterCreateBuilder().setName(newNameField);
    }

}
