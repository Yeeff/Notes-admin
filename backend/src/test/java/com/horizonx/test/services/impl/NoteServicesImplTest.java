package com.horizonx.test.services.impl;

import com.horizonx.test.domain.entities.NoteEntity;
import com.horizonx.test.domain.entities.TagEntity;
import com.horizonx.test.exceptions.ResourceNotFoundException;
import com.horizonx.test.repositories.NoteRepository;
import com.horizonx.test.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class NoteServicesImplTest {
    @Autowired
    NoteServicesImpl noteServices;
    @Autowired
    NoteRepository noteRepo;
    @Autowired
    TagRepository tagRepo;

    private static final Logger logger = LoggerFactory.getLogger(NoteServicesImplTest.class);

    @Test
    void getById() {
        /*Set<TagEntity> tagsSet = null;

        TagEntity tag = tagRepo.findById(1L)
                .orElseThrow(()-> new ResourceNotFoundException("The tag with id "+ 1L+" does not exist."));
        NoteEntity note = noteRepo.findById(1L)
                .orElseThrow(()-> new ResourceNotFoundException("The note with id "+ 1L+" does not exist."));

        tagsSet = note.getTags();
        tagsSet.add(tag);
        note.setTags(tagsSet);
        noteRepo.save(note);

        NoteEntity requestedNote = noteServices.getById(1L);

        assertEquals(note.getTags().size(), requestedNote.getTags().size());
        assertTrue(note.getTags().size() != 0 );*/

    }
}