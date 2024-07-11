package com.dev2prod.demo.mappers.impl;

import com.dev2prod.demo.domain.dtos.NoteDto;
import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper implements Mapper<NoteEntity, NoteDto> {
    private final ModelMapper modelMapper;

    public NoteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public NoteEntity mapFromDto(NoteDto noteDto) {
        return modelMapper.map(noteDto, NoteEntity.class);
    }

    @Override
    public NoteDto mapToDto(NoteEntity noteEntity) {
        return modelMapper.map(noteEntity, NoteDto.class);
    }


}
