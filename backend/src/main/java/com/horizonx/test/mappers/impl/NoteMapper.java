package com.horizonx.test.mappers.impl;

import com.horizonx.test.domain.dtos.NoteDto;
import com.horizonx.test.domain.entities.NoteEntity;
import com.horizonx.test.mappers.Mapper;
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
