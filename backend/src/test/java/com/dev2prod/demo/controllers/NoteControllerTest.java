package com.dev2prod.demo.controllers;

import com.dev2prod.demo.TestDataUtils;
import com.dev2prod.demo.domain.dtos.NoteDto;
import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.domain.entities.TagEntity;
import com.dev2prod.demo.services.NoteService;
import com.dev2prod.demo.services.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.xml.transform.sax.SAXResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    NoteService noteService;

    @Autowired
    TagService tagService;

    @Test
    void createAnNoteShouldReturnSuccessfullyHttp201Created() throws Exception {
        NoteDto noteDto = TestDataUtils.createNoteDto();
        String NoteJson = objectMapper.writeValueAsString(noteDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NoteJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    void createAnNoteShouldReturnTheSameCratedNote() throws Exception {
        NoteDto noteDto = TestDataUtils.createNoteDto();
        String noteJson = objectMapper.writeValueAsString(noteDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/notes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(noteJson)
                )
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(noteDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(noteDto.getContent()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isArchived").value(noteDto.getIsArchived()));
    }

    @Test
    void requestAllNotesShouldReturnA_listOfNotes() throws Exception {

        NoteEntity noteEntity = TestDataUtils.createNoteEntity();
        NoteEntity savedNote = noteService.saveNote(noteEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.length()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(savedNote.getTitle())
        );
    }

    @Test
    void requestA_NoteByIdShouldReturnTheCorrectNote() throws Exception {

        NoteEntity noteEntity = TestDataUtils.createNoteEntity();
        NoteEntity savedNote = noteService.saveNote(noteEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/notes/" + savedNote.getId())
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(savedNote.getTitle())
        );
    }
    @Test
    void requestANotExisting_NoteByIdShouldReturnNotFound() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/notes/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    void requestToDeleteA_NoteByIdShouldReturnNotContent() throws Exception {

        NoteEntity savedNote = noteService.saveNote(TestDataUtils.createNoteEntity());

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/notes/"+savedNote.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }



    @Test
    void requestToGetActiveNotesShouldReturnA_ListOfJustActiveNotes() throws Exception {
        NoteEntity note1 = TestDataUtils.createNoteEntity();
        NoteEntity note2 = TestDataUtils.createNoteEntity();
        note1.setIsArchived(false);
        note1.setTitle("Active");
        note2.setIsArchived(true);
        note2.setTitle("Archived");

        noteService.saveNote(note1);
        noteService.saveNote(note2);

        mockMvc.perform( MockMvcRequestBuilders.get("/api/notes/active")
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.size()").value(1)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.[0].title").value("Active")
        );

    }

    @Test
    void requestToGetArchivedNotesShouldReturnA_ListOfJustArchivedNotes() throws Exception {
        NoteEntity note1 = TestDataUtils.createNoteEntity();
        NoteEntity note2 = TestDataUtils.createNoteEntity();
        note1.setIsArchived(false);
        note1.setTitle("Active");
        note2.setIsArchived(true);
        note2.setTitle("Archived");

        noteService.saveNote(note1);
        noteService.saveNote(note2);

        mockMvc.perform( MockMvcRequestBuilders.get("/api/notes/archived")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.size()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.[0].title").value("Archived")
        );
    }

    @Test
    void requestArchiveA_NoteShouldReturnTheCorrectNoteArchived() throws Exception {
        NoteEntity note1 = TestDataUtils.createNoteEntity();
        note1.setIsArchived(false);
        note1 = noteService.saveNote(note1);

        mockMvc.perform( MockMvcRequestBuilders.patch("/api/notes/archive/" + note1.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isArchived").value(true)
        );

    }

    @Test
    void requestUnrchiveA_NoteShouldReturnTheCorrectNoteUnrchived() throws Exception {
        NoteEntity note1 = TestDataUtils.createNoteEntity();
        note1.setIsArchived(true);
        note1 = noteService.saveNote(note1);

        mockMvc.perform( MockMvcRequestBuilders.patch("/api/notes/unarchive/" + note1.getId())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isArchived").value(false)
        );
    }

    @Test
    void requestAssignA_TagShouldReturnA_NoteWithA_ListWithTheTagAssigned() throws Exception {
        NoteEntity noteSaved = noteService.saveNote(TestDataUtils.createNoteEntity());

        TagEntity tagSaved = TestDataUtils.createTagEntity();
        tagSaved.setName("Some tag name");
        tagSaved = tagService.saveTag(tagSaved);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/notes/"+ noteSaved.getId()+"/addTag/"+ tagSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags.size()").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags[0].name").value("Some tag name")
        );

    }

    @Test
    void requestRemoveA_TagShouldReturnA_NoteWithA_ListWithoutTheTag() throws Exception {
        NoteEntity noteSaved = noteService.saveNote(TestDataUtils.createNoteEntity());

        TagEntity tagSaved = TestDataUtils.createTagEntity();
        tagSaved = tagService.saveTag(tagSaved);

        noteService.assignTagToNote(noteSaved.getId(), tagSaved.getId());

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/notes/"+ noteSaved.getId()+"/removeTag/"+ tagSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.tags.size()").value(0)
        );
    }

    @Test
    void requestGetActivesNotesByTagIdShouldReturnA_ListOfNotesUnderThatTag() throws Exception {
        NoteEntity noteSaved = noteService.saveNote(TestDataUtils.createNoteEntity());

        TagEntity tagSaved = TestDataUtils.createTagEntity();
        tagSaved = tagService.saveTag(tagSaved);

        noteService.assignTagToNote(noteSaved.getId(), tagSaved.getId());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/notes/activesByTag/"+tagSaved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.size()").value("1")
        );

    }
}