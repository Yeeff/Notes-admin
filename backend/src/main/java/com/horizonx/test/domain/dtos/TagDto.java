package com.horizonx.test.domain.dtos;

import com.horizonx.test.domain.entities.NoteEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

