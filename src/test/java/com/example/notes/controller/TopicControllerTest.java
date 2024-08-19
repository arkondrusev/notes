package com.example.notes.controller;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.topic.*;
import com.example.notes.service.TopicService;
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
import static com.example.notes.generator.topic.TopicDtoGenerator.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TopicController.class)
public class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private TopicController topicController;

    @MockBean
    private TopicService topicService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    void getTopicTree_success() throws Exception {
        GetTopicTreeResponse resp = generateGetTopicTreeResponseBuilder().build();

        when(topicService.getTopicTree()).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/topic/get-tree"))
                .andExpect(status().isOk());

        verify(topicService).getTopicTree();
        verifyNoMoreInteractions(topicService);
    }

    @Test
    @WithMockUser
    void createTopic_success() throws Exception {
        CreateTopicRequest req = generateCreateTopicRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        CreateTopicResponse resp = generateCreateTopicResponseBuilder().build();

        when(topicService.getTopicTree()).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/topic/create")
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(topicService).createTopic(req);
        verifyNoMoreInteractions(topicService);
    }

    @Test
    @WithMockUser
    void updateTopic_success() throws Exception {
        UpdateTopicRequest req = generateUpdateTopicRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        OperationResponse resp = generateOperationResponseOkBuilder().build();

        when(topicService.getTopicTree()).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/topic/update")
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(topicService).updateTopic(req);
        verifyNoMoreInteractions(topicService);
    }

    @Test
    @WithMockUser
    void deleteTopic_success() throws Exception {
        DeleteTopicRequest req = generateDeleteTopicRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        OperationResponse resp = generateOperationResponseOkBuilder().build();

        when(topicService.getTopicTree()).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/topic/delete")
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(topicService).deleteTopic(req);
        verifyNoMoreInteractions(topicService);
    }

}
