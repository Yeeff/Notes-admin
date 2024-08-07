package com.dev2prod.demo.controllers;

import com.dev2prod.demo.domain.dtos.NoteDto;
import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.exceptions.ResourceNotFoundException;
import com.dev2prod.demo.mappers.impl.NoteMapper;
import com.dev2prod.demo.services.NoteService;
import com.dev2prod.demo.utils.SecurityUtils;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteService;
    private NoteMapper mapper;

    private static final Logger logger= LoggerFactory.getLogger(NoteController.class);

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
    public ResponseEntity<List<NoteDto>> getActiveNotes() {

        return ResponseEntity.ok(noteService.findNotesByUsernameAndActive()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/archived")
    public List<NoteDto> getArchivedNotes() {
        return noteService.findNotesByUsernameAndArchived()
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
    public ResponseEntity<NoteDto> assignTag(
            @PathVariable Long noteId,
            @PathVariable Long tagId){

        NoteDto noteDto = mapper.mapToDto(noteService.assignTagToNote(noteId, tagId));

        return ResponseEntity.status(HttpStatus.OK).body(noteDto);
    }

    @PatchMapping("{noteId}/removeTag/{tagId}")
    public ResponseEntity<NoteDto> removeTag(
            @PathVariable Long noteId,
            @PathVariable Long tagId){

        NoteDto noteDto = mapper.mapToDto(noteService.removeTagToNote(noteId, tagId));

        return ResponseEntity.status(HttpStatus.OK).body(noteDto);
    }

    @GetMapping("/activesByTag/{tagId}")
    public List<NoteDto> getActivesNotesByTagId(@PathVariable Long tagId){
        return noteService.getActivesByTagId(tagId)
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

}
