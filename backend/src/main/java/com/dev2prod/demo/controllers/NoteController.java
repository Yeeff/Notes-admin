package com.dev2prod.demo.controllers;

import com.dev2prod.demo.domain.dtos.NoteDto;
import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.exceptions.ResourceNotFoundException;
import com.dev2prod.demo.mappers.impl.NoteMapper;
import com.dev2prod.demo.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteService;
    private NoteMapper mapper;

    public NoteController(NoteService noteServices, NoteMapper mapper) {
        this.noteService = noteServices;
        this.mapper = mapper;
    }


    @PostMapping
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto note){
        note.setId(null);
        NoteEntity noteEntity = mapper.mapFromDto(note);
        return new ResponseEntity<>(
                mapper.mapToDto(noteService.saveNote(noteEntity)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id){
        return
                ResponseEntity.status(HttpStatus.OK)
                        .body(mapper.mapToDto(
                                noteService.getById(id)
                        ));
    }

    @GetMapping
    public List<NoteDto> getAllNotes(){
        return noteService.getAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> noteFullUpdate(
            @PathVariable Long id,
            @RequestBody NoteDto noteDto){
        if(!noteService.existNoteById(id)){
            throw new ResourceNotFoundException("The note with id " + id + " does not exist");
        }

        noteDto.setId(id);
        NoteEntity noteEntity = mapper.mapFromDto(noteDto);

        return new ResponseEntity<>(mapper.mapToDto(noteService.saveNote(noteEntity)),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteDto> notePartialUpdate(
            @PathVariable Long id,
            @RequestBody NoteDto noteDto){

        if(!noteService.existNoteById(id)){
            throw new ResourceNotFoundException("The note with id " + id + " does not exist");
        }

        NoteEntity noteEntity = mapper.mapFromDto(noteDto);
        return new ResponseEntity<>(mapper.mapToDto(
                noteService.partialUpdate(id,noteEntity)),HttpStatus.OK );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NoteDto> deleteNote(@PathVariable Long id){
        noteService.deleteNote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active")
    public List<NoteDto> getActiveNotes() {
        return noteService.getActiveNotes()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/archived")
    public List<NoteDto> getArchivedNotes() {
        return noteService.getArchivedNotes()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
    @PatchMapping("/archive/{id}")
    public NoteDto archiveNote (@PathVariable Long id) {
        return mapper.mapToDto(noteService.archiveNote(id));
    }

    @PatchMapping("/unarchive/{id}")
    public NoteDto unarchiveNote (@PathVariable Long id) {
        return mapper.mapToDto(noteService.unarchiveNote(id));
    }

    @PatchMapping("{noteId}/addTag/{tagId}")
    public ResponseEntity<NoteEntity> assignTag(
            @PathVariable Long noteId,
            @PathVariable Long tagId){
        return ResponseEntity.status(HttpStatus.OK).body(noteService.assignTagToNote(noteId, tagId));
    }

    @PatchMapping("{noteId}/removeTag/{tagId}")
    public ResponseEntity<NoteEntity> removeTag(
            @PathVariable Long noteId,
            @PathVariable Long tagId){
        return ResponseEntity.status(HttpStatus.OK).body(noteService.removeTagToNote(noteId, tagId));
    }

    @GetMapping("/activesByTag/{tagId}")
    public List<NoteEntity> getActivesNotesByTagId(@PathVariable Long tagId){
        return noteService.getActivesByTagId(tagId);
    }

    @GetMapping("/user")
    public ResponseEntity<List<NoteDto>> getNotesByUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<NoteDto> notes = noteService.findNotesByUsernameAndArchived(username)
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(notes);
    }

}
