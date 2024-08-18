package com.example.notes.controller;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.tag.*;
import com.example.notes.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.example.notes.generator.OperationResponseGenerator.generateOperationResponseOkBuilder;
import static com.example.notes.generator.tag.TagDtoGenerator.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private TagController tagController;

    @MockBean
    private TagService tagService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    void getTagList_success() throws Exception {
        GetTagListResponse resp = generateGetTagListResponseBuilder().build();

        when(tagService.getTagList()).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tag/get-list"))
                .andExpect(status().isOk());

        verify(tagService).getTagList();
        verifyNoMoreInteractions(tagService);
    }


    @Test
    @WithMockUser
    void createTag_success() throws Exception {
        CreateTagRequest req = generateCreateTagRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        CreateTagResponse resp = generateCreateTagResponseBuilder().build();

        when(tagService.createTag(req)).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/tag/create")
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());

        verify(tagService).createTag(req);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    @WithMockUser
    void updateTag_success() throws Exception {
        UpdateTagRequest req = generateUpdateTagRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        OperationResponse resp = generateOperationResponseOkBuilder().build();

        when(tagService.updateTag(req)).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/tag/update")
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());

        verify(tagService).updateTag(req);
        verifyNoMoreInteractions(tagService);
    }

    @Test
    @WithMockUser
    void deleteTag_success() throws Exception {
        DeleteTagRequest req = generateDeleteTagRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        OperationResponse resp = generateOperationResponseOkBuilder().build();

        when(tagService.deleteTag(req)).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/tag/delete")
                .content(stringReq)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(tagService).deleteTag(req);
        verifyNoMoreInteractions(tagService);
    }

}
