package com.horizonx.test.mappers.impl;

import com.horizonx.test.domain.dtos.TagDto;
import com.horizonx.test.domain.entities.TagEntity;
import com.horizonx.test.mappers.Mapper;
import jakarta.persistence.Column;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapper implements Mapper<TagEntity, TagDto> {

    private final ModelMapper mapper;

    public TagMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TagEntity mapFromDto(TagDto tagDto) {
        return mapper.map(tagDto, TagEntity.class);
    }

    @Override
    public TagDto mapToDto(TagEntity tagEntity) {
        return mapper.map(tagEntity, TagDto.class);
    }
}
