package com.dev2prod.demo.services;

import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.exceptions.ResourceNotFoundException;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NoteServiceTest {
    @Autowired
    NoteService noteService;

    NoteEntity note1;
    NoteEntity note2;

    @BeforeEach
    public void setUp(){
        note1 = NoteEntity.builder()
                .title("Title Test 1")
                .content("Content test 1")
                .isArchived(false)
                .build();

        note2 = NoteEntity.builder()
                .title("Title Test 2")
                .content("Content test 2")
                .isArchived(false)
                .build();

    }

    @Test
    void SavedNoteShouldBeTheSameNoteRequestedByID() {
        NoteEntity noteSaved = noteService.saveNote(note1);

        NoteEntity noteRequested = noteService.getById(noteSaved.getId());
        assertEquals(noteSaved.getTitle(), noteRequested.getTitle());
        assertEquals(noteSaved.getContent(), noteRequested.getContent());
        assertEquals(noteSaved.getIsArchived(), noteRequested.getIsArchived());
    }

    @Test
    void requestANotExisting_NoteByIdShouldReturnNotFound() {
        assertThrows( ResourceNotFoundException.class, ()-> noteService.getById(1L));
    }

    @Test
    void requestAllNotesShouldReturnA_CorrectQuantityOfNotes() {
        noteService.saveNote(note1);
        noteService.saveNote(note2);

        List<NoteEntity> noteList = noteService.getAll();
        assertEquals(2, noteList.size());

    }

    @Test
    void requestToMakeA_PartialUpdate_shouldUpdateTheNote() {
        NoteEntity noteSaved = noteService.saveNote(note1);

        noteSaved.setTitle("Title Updated");
        noteService.partialUpdate(noteSaved.getId(),noteSaved);

        NoteEntity updatedNote = noteService.getById(noteSaved.getId());

        assertEquals(updatedNote.getTitle(), "Title Updated");

    }

    @Test
    void requestToDeleteA_Not_Existing_Note_shouldReturnAResourceNotFoundException() {

        NoteEntity noteSaved = noteService.saveNote(note1);

        noteService.deleteNote(noteSaved.getId());

        assertThrows( ResourceNotFoundException.class, () -> {
            noteService.getById(note1.getId());
        });


    }

    /*@Test
    void getActiveNotes() {
    }

    @Test
    void getArchivedNotes() {
    }

    @Test
    void archiveNote() {
    }

    @Test
    void unarchiveNote() {
    }

    @Test
    void assignTagToNote() {
    }

    @Test
    void removeTagToNote() {
    }

    @Test
    void getActivesByTagId() {
    }*/
}