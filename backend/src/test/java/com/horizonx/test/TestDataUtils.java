package com.horizonx.test;

import com.horizonx.test.domain.dtos.NoteDto;

public class TestDataUtils {
    static public NoteDto createNoteDtoA(){
        return NoteDto.builder()
                .title("Tasks")
                .content("This is some content")
                .isArchived(false)
                .build();
    }

}
