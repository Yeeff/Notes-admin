package com.dev2prod.demo.repositories;

import com.dev2prod.demo.domain.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByIsArchivedFalse();
    List<NoteEntity> findByIsArchivedTrue();
    Boolean existsByTitle(String title);
    Optional<NoteEntity> findByTitle(String title);
}
