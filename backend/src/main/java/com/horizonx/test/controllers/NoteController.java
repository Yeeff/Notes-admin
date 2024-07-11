package com.horizonx.test.controllers;

import com.horizonx.test.domain.dtos.NoteDto;
import com.horizonx.test.domain.entities.NoteEntity;
import com.horizonx.test.exceptions.ResourceNotFoundException;
import com.horizonx.test.mappers.impl.NoteMapper;
import com.horizonx.test.services.impl.NoteServicesImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private NoteServicesImpl services;

    private NoteMapper mapper;

    public NoteController(NoteServicesImpl services, NoteMapper mapper) {
        this.services = services;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto note){
        note.setId(null);
        NoteEntity noteEntity = mapper.mapFromDto(note);
        return new ResponseEntity<>(
                mapper.mapToDto(services.saveNote(noteEntity)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id){
        return
                ResponseEntity.status(HttpStatus.OK)
                        .body(mapper.mapToDto(
                                services.getById(id)
                        ));
    }

    @GetMapping
    public List<NoteDto> getAllNotes(){
        return services.getAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> noteFullUpdate(
            @PathVariable Long id,
            @RequestBody NoteDto noteDto){
        if(!services.existNoteById(id)){
            throw new ResourceNotFoundException("The note with id " + id + " does not exist");
        }

        noteDto.setId(id);
        NoteEntity noteEntity = mapper.mapFromDto(noteDto);

        return new ResponseEntity<>(mapper.mapToDto(services.saveNote(noteEntity)),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteDto> notePartialUpdate(
            @PathVariable Long id,
            @RequestBody NoteDto noteDto){

        if(!services.existNoteById(id)){
            throw new ResourceNotFoundException("The note with id " + id + " does not exist");
        }

        NoteEntity noteEntity = mapper.mapFromDto(noteDto);
        return new ResponseEntity<>(mapper.mapToDto(
                services.partialUpdate(id,noteEntity)),HttpStatus.OK );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NoteDto> deleteProduct(@PathVariable Long id){
        services.deleteNote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active")
    public List<NoteDto> getActiveNotes() {
        return services.getActiveNotes()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/archived")
    public List<NoteDto> getArchivedNotes() {
        return services.getArchivedNotes()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
    @PatchMapping("/archive/{id}")
    public NoteDto archiveNote (@PathVariable Long id) {
        return mapper.mapToDto(services.archiveNote(id));
    }

    @PatchMapping("/unarchive/{id}")
    public NoteDto unarchiveNote (@PathVariable Long id) {
        return mapper.mapToDto(services.unarchiveNote(id));
    }

    @PatchMapping("{noteId}/tag/{tagId}")
    public ResponseEntity<NoteEntity> assignTag(
            @PathVariable Long noteId,
            @PathVariable Long tagId){
        return ResponseEntity.status(HttpStatus.OK).body(services.assignTagToNote(noteId, tagId));
    }
}
