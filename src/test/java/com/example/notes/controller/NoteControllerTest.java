package com.example.notes.controller;

import com.example.notes.dto.OperationResponse;
import com.example.notes.dto.note.*;
import com.example.notes.service.NoteService;
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
import static com.example.notes.generator.note.NoteDtoGenerator.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @InjectMocks
    private NoteController noteController;

    @MockBean
    private NoteService noteService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser
    void getNoteList_success() throws Exception {
        GetNoteListResponse resp = generateGetNoteListResponseBuilder().build();

        when(noteService.getNoteList()).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/note/get-list"))
                .andExpect(status().isOk());

        verify(noteService).getNoteList();
        verifyNoMoreInteractions(noteService);
    }

    @Test
    @WithMockUser
    void createNote_success() throws Exception {
        CreateNoteRequest req = generateCreateNoteRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        CreateNoteResponse resp = generateCreateNoteResponseBuilder().build();

        when(noteService.createNote(req)).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/note/create")
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());

        verify(noteService).createNote(req);
        verifyNoMoreInteractions(noteService);
    }

    @Test
    @WithMockUser
    void updateNote_success() throws Exception {
        UpdateNoteRequest req = generateUpdateNoteRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        OperationResponse resp = generateOperationResponseOkBuilder().build();

        when(noteService.updateNote(req)).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/note/update")
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());

        verify(noteService).updateNote(req);
        verifyNoMoreInteractions(noteService);
    }

    @Test
    @WithMockUser
    void deleteNote_success() throws Exception {
        DeleteNoteRequest req = generateDeleteNoteRequestBuilder().build();
        String stringReq = objectMapper.writeValueAsString(req);
        OperationResponse resp = generateOperationResponseOkBuilder().build();

        when(noteService.deleteNote(req)).thenReturn(resp);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/note/delete")
                        .content(stringReq)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk());

        verify(noteService).deleteNote(req);
        verifyNoMoreInteractions(noteService);
    }

}
