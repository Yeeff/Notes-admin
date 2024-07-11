package com.dev2prod.demo.mappers.impl;

import com.dev2prod.demo.domain.dtos.TagDto;
import com.dev2prod.demo.domain.entities.TagEntity;
import com.dev2prod.demo.mappers.Mapper;
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
