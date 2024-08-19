package com.example.notes.generator.tag;

import com.example.notes.dto.tag.*;

import java.util.HashSet;
import java.util.Set;

public class TagDtoGenerator {

    public static final Integer TAG_ID_1 = 1;
    public static final Integer TAG_ID_2 = 2;
    public static final String TAG_NAME_1 = "TAG 1";
    public static final String TAG_NAME_1_UPDATED = "TAG 1 UPDATED";
    public static final String TAG_NAME_2 = "TAG 2";

    public static GetTagListResponse.GetTagListResponseBuilder generateGetTagListResponseBuilder() {
        Set<TagWrapper> tagWrapperList = new HashSet<>();
        tagWrapperList.add(new TagWrapper(TAG_ID_1, TAG_NAME_1));
        tagWrapperList.add(new TagWrapper(TAG_ID_2, TAG_NAME_2));
        return GetTagListResponse.builder().setTagList(tagWrapperList);
    }

    public static CreateTagRequest.CreateTagRequestBuilder generateCreateTagRequestBuilder() {
        return CreateTagRequest.builder().setTagName(TAG_NAME_1);
    }

    public static CreateTagResponse.CreateTagResponseBuilder generateCreateTagResponseBuilder() {
        return CreateTagResponse.builder().setTagId(TAG_ID_1).setTagName(TAG_NAME_1)
                .setResultCode(0).setResultMessage("OK");
    }

    public static UpdateTagRequest.UpdateTagRequestBuilder generateUpdateTagRequestBuilder() {
        return UpdateTagRequest.builder().setTagName(TAG_NAME_1_UPDATED);
    }

    public static DeleteTagRequest.DeleteTagRequestBuilder generateDeleteTagRequestBuilder() {
        return DeleteTagRequest.builder().setTagId(TAG_ID_1);
    }

}
