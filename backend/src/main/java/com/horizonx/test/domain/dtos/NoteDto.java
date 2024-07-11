package com.horizonx.test.domain.dtos;

import com.horizonx.test.domain.entities.TagEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDto{
    private Long id;
    @NotNull(message = "The title is mandatory.")
    private String title;

    private String content;
    private Boolean isArchived = false;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    private Set<TagEntity> tags = new HashSet<>();

}