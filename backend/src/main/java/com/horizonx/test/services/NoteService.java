package com.horizonx.test.services;


import com.horizonx.test.domain.entities.NoteEntity;

import java.util.List;

public interface NoteService {

    NoteEntity saveNote(NoteEntity note) ;
    NoteEntity getById(Long id);
    List<NoteEntity> getAll();

    Boolean existNoteById(Long id);
    Boolean existByTitle(String title);
    NoteEntity partialUpdate(Long id, NoteEntity NoteDetails);
    void deleteNote(Long id) ;
    List<NoteEntity> getActiveNotes();
    List<NoteEntity> getArchivedNotes() ;
    NoteEntity archiveNote (Long id) ;
    public NoteEntity unarchiveNote (Long id);
}
