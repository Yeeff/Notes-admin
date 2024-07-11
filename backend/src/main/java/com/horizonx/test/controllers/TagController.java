package com.horizonx.test.controllers;

import com.horizonx.test.domain.dtos.TagDto;
import com.horizonx.test.domain.entities.TagEntity;
import com.horizonx.test.mappers.Mapper;
import com.horizonx.test.mappers.impl.TagMapper;
import com.horizonx.test.services.impl.TagServicesImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagServicesImpl tagServices;
    private final TagMapper mapper;

    public TagController(TagServicesImpl tagServices, TagMapper mapper) {
        this.tagServices = tagServices;
        this.mapper = mapper;
    }

    @GetMapping
    public List<TagDto> getAllTags(){
        return tagServices.getAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
