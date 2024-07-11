package com.dev2prod.demo.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto {

    private Long id;
    @NotNull(message = "The tag name is mandatory.")
    private String name ;

    //private Set<NoteEntity> notes = new HashSet<>();
}

