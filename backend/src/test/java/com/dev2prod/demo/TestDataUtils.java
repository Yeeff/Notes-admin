package com.dev2prod.demo;

import com.dev2prod.demo.domain.dtos.NoteDto;
import com.dev2prod.demo.domain.entities.NoteEntity;
import com.dev2prod.demo.domain.entities.TagEntity;

public class TestDataUtils {

    static public NoteDto createNoteDto(){
        return NoteDto.builder()
                .title("Note title")
                .content("content note")
                .isArchived(false)
                .build();
    }
    static public NoteEntity createNoteEntity(){
        return NoteEntity.builder()
                .title("Note title")
                .content("content note")
                .isArchived(false)
                .build();
    }
    static public TagEntity createTagEntity(){
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName("Tag1");
        return tagEntity;
    }
}
