package com.dev2prod.demo.controllers;

import com.dev2prod.demo.domain.dtos.NoteDto;
import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.exceptions.ResourceNotFoundException;
import com.dev2prod.demo.mappers.impl.NoteMapper;
import com.dev2prod.demo.services.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteServices;
    private NoteMapper mapper;

    public NoteController(NoteService noteServices, NoteMapper mapper) {
        this.noteServices = noteServices;
        this.mapper = mapper;
    }


    @PostMapping
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto note){
        note.setId(null);
        NoteEntity noteEntity = mapper.mapFromDto(note);
        return new ResponseEntity<>(
                mapper.mapToDto(noteServices.saveNote(noteEntity)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id){
        return
                ResponseEntity.status(HttpStatus.OK)
                        .body(mapper.mapToDto(
                                noteServices.getById(id)
                        ));
    }

    @GetMapping
    public List<NoteDto> getAllNotes(){
        return noteServices.getAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> noteFullUpdate(
            @PathVariable Long id,
            @RequestBody NoteDto noteDto){
        if(!noteServices.existNoteById(id)){
            throw new ResourceNotFoundException("The note with id " + id + " does not exist");
        }

        noteDto.setId(id);
        NoteEntity noteEntity = mapper.mapFromDto(noteDto);

        return new ResponseEntity<>(mapper.mapToDto(noteServices.saveNote(noteEntity)),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteDto> notePartialUpdate(
            @PathVariable Long id,
            @RequestBody NoteDto noteDto){

        if(!noteServices.existNoteById(id)){
            throw new ResourceNotFoundException("The note with id " + id + " does not exist");
        }

        NoteEntity noteEntity = mapper.mapFromDto(noteDto);
        return new ResponseEntity<>(mapper.mapToDto(
                noteServices.partialUpdate(id,noteEntity)),HttpStatus.OK );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NoteDto> deleteProduct(@PathVariable Long id){
        noteServices.deleteNote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active")
    public List<NoteDto> getActiveNotes() {
        return noteServices.getActiveNotes()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/archived")
    public List<NoteDto> getArchivedNotes() {
        return noteServices.getArchivedNotes()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
    @PatchMapping("/archive/{id}")
    public NoteDto archiveNote (@PathVariable Long id) {
        return mapper.mapToDto(noteServices.archiveNote(id));
    }

    @PatchMapping("/unarchive/{id}")
    public NoteDto unarchiveNote (@PathVariable Long id) {
        return mapper.mapToDto(noteServices.unarchiveNote(id));
    }

    @PatchMapping("{noteId}/addTag/{tagId}")
    public ResponseEntity<NoteEntity> assignTag(
            @PathVariable Long noteId,
            @PathVariable Long tagId){
        return ResponseEntity.status(HttpStatus.OK).body(noteServices.assignTagToNote(noteId, tagId));
    }

    @PatchMapping("{noteId}/removeTag/{tagId}")
    public ResponseEntity<NoteEntity> removeTag(
            @PathVariable Long noteId,
            @PathVariable Long tagId){
        return ResponseEntity.status(HttpStatus.OK).body(noteServices.removeTagToNote(noteId, tagId));
    }

}