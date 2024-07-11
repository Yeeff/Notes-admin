package com.horizonx.test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizonx.test.TestDataUtils;
import com.horizonx.test.domain.dtos.NoteDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createAnNoteShouldReturnSuccessfullyHttp201Created() throws Exception {
        NoteDto noteDto = TestDataUtils.createNoteDtoA();
        String noteJson = objectMapper.writeValueAsString(noteDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }
    @Test
    void createAnProductShouldReturnTheSameCratedProduct() throws Exception {
        NoteDto noteDto = TestDataUtils.createNoteDtoA();
        String noteJson = objectMapper.writeValueAsString(noteDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/notes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(noteJson)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(noteDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(noteDto.getContent()));
    }
}