package com.dev2prod.demo.controllers;

import com.dev2prod.demo.domain.dtos.TagDto;
import com.dev2prod.demo.domain.entities.TagEntity;
import com.dev2prod.demo.mappers.impl.TagMapper;
import com.dev2prod.demo.services.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private TagService tagServices;
    private final TagMapper mapper;

    public TagController(TagService tagServices, TagMapper mapper) {
        this.tagServices = tagServices;
        this.mapper = mapper;
    }

    @PostMapping("/save")
    public ResponseEntity createProject(@RequestBody TagEntity projectObj) {
        tagServices.saveProject(projectObj);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = {"/getProjects", "/{projectId}"})
    public List<TagEntity> getProjects(@PathVariable(required = false) Long projectId) {
        return tagServices.getProjectDetails(projectId);
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity removeProject(@PathVariable Long projectId) {
        tagServices.deleteProject(projectId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public List<TagDto> getAllTags(){
        return tagServices.getAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
