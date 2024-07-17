package com.dev2prod.demo.services;

import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.domain.entities.TagEntity;
import com.dev2prod.demo.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NoteServiceTest {
    @Autowired
    NoteService noteService;
    @Autowired
    TagService tagService;

    NoteEntity note1;
    NoteEntity note2;

    TagEntity tag;

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

        tag = new TagEntity();
        tag.setName("Title");

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
        assertThrows( ResourceNotFoundException.class, () -> {
            noteService.getById(1L);
        });
    }

    @Test
    void requestToDeleteA_Note_shouldReturnHttpNoContent() {

        NoteEntity noteSaved = noteService.saveNote(note1);

        noteService.deleteNote(noteSaved.getId());

        assertThrows( ResourceNotFoundException.class, () -> {
            noteService.getById(noteSaved.getId());
        });

    }

    @Test
    void requestGetActiveNotesShouldReturnJustActives() {
        note1.setIsArchived(false);
        note2.setIsArchived(true);
        noteService.saveNote(note1);
        noteService.saveNote(note2);

        List<NoteEntity> notesList = noteService.getActiveNotes();

        assertEquals(1, notesList.size());
        assertEquals(false, notesList.get(0).getIsArchived());
    }

    @Test
    void requestGetArchivedNotesShouldReturnJustArchived() {
        note1.setIsArchived(true);
        note2.setIsArchived(false);
        noteService.saveNote(note1);
        noteService.saveNote(note2);

        List<NoteEntity> notesList = noteService.getArchivedNotes();

        assertEquals(1, notesList.size());
        assertEquals(true, notesList.get(0).getIsArchived());
    }

    @Test
    void requestArchiveA_NoteShouldArchiveThatNote() {
        note1.setIsArchived(false);
        noteService.saveNote(note1);

        noteService.archiveNote(note1.getId());

        NoteEntity noteSaved = noteService.getById(note1.getId());

        assertEquals(true, noteSaved.getIsArchived());
    }

    @Test
    void requestUnArchiveA_NoteShouldUnArchiveThatNote() {
        note1.setIsArchived(true);
        noteService.saveNote(note1);

        noteService.unarchiveNote(note1.getId());

        NoteEntity noteSaved = noteService.getById(note1.getId());

        assertEquals(false, noteSaved.getIsArchived());
    }
}