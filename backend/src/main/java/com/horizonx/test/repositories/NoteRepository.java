package com.horizonx.test.repositories;

import com.horizonx.test.domain.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByIsArchivedFalse();
    List<NoteEntity> findByIsArchivedTrue();

    Boolean existsByTitle(String title);
    Optional<NoteEntity> findByTitle(String title);
}
