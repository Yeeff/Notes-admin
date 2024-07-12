package com.dev2prod.demo.services;

import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.exceptions.EntityAlreadyExistException;
import com.dev2prod.demo.exceptions.ResourceNotFoundException;
import com.dev2prod.demo.repositories.NoteRepository;
import com.dev2prod.demo.domain.entities.TagEntity;
import com.dev2prod.demo.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NoteService {
    private NoteRepository noteRepo;
    private TagRepository tagRepo;

    public NoteService(NoteRepository noteRepo, TagRepository tagRepository) {
        this.noteRepo = noteRepo;
        this.tagRepo = tagRepository;
    }

    public NoteEntity saveNote(NoteEntity note) {
        try{
            return noteRepo.save(note);
        }catch (Exception e){
            if (e.getMessage().contains("already exists") ) {
                throw new EntityAlreadyExistException("The Note already exist.");
            }else{
                throw new RuntimeException(
                        "Error occurred. error: " + e.getMessage()
                );
            }
        }
    }

    public NoteEntity getById(Long id) {
        NoteEntity note =noteRepo.findById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("The Note with id " + id + " does not exist.")
                );
        return note;
    }

    public List<NoteEntity> getAll() {
        return noteRepo.findAll() ;
    }


    public Boolean existNoteById(Long id) {
        return noteRepo.existsById(id);
    }


    public NoteEntity partialUpdate(Long id, NoteEntity note) {
        return noteRepo.findById(id)
                .map(existingNote -> {
                    Optional.ofNullable(note.getTitle()).ifPresent(existingNote::setTitle);
                    Optional.ofNullable(note.getContent()).ifPresent(existingNote::setContent);
                    Optional.ofNullable(note.getIsArchived()).ifPresent(existingNote::setIsArchived);
                    Optional.ofNullable(note.getUpdatedAt()).ifPresent(existingNote::setUpdatedAt);

                    return noteRepo.save(existingNote);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "The note could not be partial updated because of note with id " + id + ", does not exist"
                        )
                );
    }

    public void deleteNote(Long id) {
        noteRepo.deleteById(id);
    }

    @Transactional
    public List<NoteEntity> getActiveNotes() {
        return noteRepo.findByIsArchivedFalse();
    }

    @Transactional
    public List<NoteEntity> getArchivedNotes() {
        return noteRepo.findByIsArchivedTrue();
    }

    public NoteEntity archiveNote(Long id) {
        NoteEntity note = noteRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        note.setIsArchived(true);
        return noteRepo.save(note);
    }

    public NoteEntity unarchiveNote(Long id) {
        NoteEntity note = noteRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        note.setIsArchived(false);
        return noteRepo.save(note);
    }

    @Transactional
    public NoteEntity assignTagToNote(Long noteId, Long tagId){
        Set<TagEntity> tagsSet = null;

        TagEntity tag = tagRepo.findById(tagId)
                .orElseThrow(()-> new ResourceNotFoundException("The tag with id "+ tagId+" does not exist."));
        NoteEntity note = noteRepo.findById(noteId)
                .orElseThrow(()-> new ResourceNotFoundException("The note with id "+ noteId+" does not exist."));

        tagsSet = note.getTags();
        tagsSet.add(tag);
        note.setTags(tagsSet);

        return noteRepo.save(note);
    }

    @Transactional
    public NoteEntity removeTagToNote(Long noteId, Long tagId){
        Set<TagEntity> tagsSet = null;

        TagEntity tag = tagRepo.findById(tagId)
                .orElseThrow(()-> new ResourceNotFoundException("The tag with id "+ tagId+" does not exist."));
        NoteEntity note = noteRepo.findById(noteId)
                .orElseThrow(()-> new ResourceNotFoundException("The note with id "+ noteId+" does not exist."));

        tagsSet = note.getTags();
        tagsSet.remove(tag);
        note.setTags(tagsSet);

        return noteRepo.save(note);
    }

    @Transactional
    public List<NoteEntity> getActivesByTagId( Long id){
        return noteRepo.findByIsArchivedFalseAndTags_Id(id);
    }
}
