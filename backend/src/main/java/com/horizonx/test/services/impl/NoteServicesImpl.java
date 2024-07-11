package com.horizonx.test.services.impl;

import com.horizonx.test.domain.entities.NoteEntity;
import com.horizonx.test.domain.entities.TagEntity;
import com.horizonx.test.exceptions.EntityAlreadyExistException;
import com.horizonx.test.exceptions.ResourceNotFoundException;
import com.horizonx.test.repositories.NoteRepository;
import com.horizonx.test.repositories.TagRepository;
import com.horizonx.test.services.NoteService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NoteServicesImpl implements NoteService {
    private final NoteRepository repo;
    private final TagRepository tagRepo;

    private static final Logger logger = LoggerFactory.getLogger(NoteServicesImpl.class);

    public NoteServicesImpl(NoteRepository repo, TagRepository tagRepo) {
        this.repo = repo;
        this.tagRepo = tagRepo;
    }

    public NoteEntity saveNote(NoteEntity note) {
        try{
            return repo.save(note);
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
        NoteEntity note =repo.findById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("The Note with id " + id + " does not exist.")
                );
        logger.info("------------>" + repo.findById(id).get().getTags() );
        return note;
    }

    public List<NoteEntity> getAll() {
        return repo.findAll() ;
    }

    @Override
    public Boolean existNoteById(Long id) {
        return repo.existsById(id);
    }

    @Override
    public Boolean existByTitle(String title) {
        return repo.existsByTitle(title);
    }

    public NoteEntity partialUpdate(Long id, NoteEntity note) {
        return repo.findById(id)
                .map(existingNote -> {
                    Optional.ofNullable(note.getTitle()).ifPresent(existingNote::setTitle);
                    Optional.ofNullable(note.getContent()).ifPresent(existingNote::setContent);
                    Optional.ofNullable(note.getIsArchived()).ifPresent(existingNote::setIsArchived);
                    Optional.ofNullable(note.getUpdatedAt()).ifPresent(existingNote::setUpdatedAt);

                    return repo.save(existingNote);
                }).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "The note could not be partial updated because of note with id " + id + ", does not exist"
                        )
                );
    }

    public void deleteNote(Long id) {
        repo.deleteById(id);
    }

    @Transactional
    public List<NoteEntity> getActiveNotes() {
        return repo.findByIsArchivedFalse();
    }

    @Transactional
    public List<NoteEntity> getArchivedNotes() {
        return repo.findByIsArchivedTrue();
    }

    public NoteEntity archiveNote(Long id) {
        NoteEntity note = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        note.setIsArchived(true);
        return repo.save(note);
    }

    public NoteEntity unarchiveNote(Long id) {
        NoteEntity note = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found"));
        note.setIsArchived(false);
        return repo.save(note);
    }

    @Transactional
    public NoteEntity assignTagToNote(Long noteId, Long tagId){
        Set<TagEntity> tagsSet = null;

        TagEntity tag = tagRepo.findById(tagId)
                .orElseThrow(()-> new ResourceNotFoundException("The tag with id "+ tagId+" does not exist."));
        NoteEntity note = repo.findById(noteId)
                .orElseThrow(()-> new ResourceNotFoundException("The note with id "+ noteId+" does not exist."));

        tagsSet = note.getTags();
        tagsSet.add(tag);
        note.setTags(tagsSet);

        return repo.save(note);
    }
}
