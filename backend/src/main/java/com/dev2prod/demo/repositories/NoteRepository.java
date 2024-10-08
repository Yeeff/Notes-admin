package com.dev2prod.demo.repositories;

import com.dev2prod.demo.domain.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    List<NoteEntity> findByIsArchivedFalse();
    List<NoteEntity> findByIsArchivedTrue();
    List<NoteEntity> findByIsArchivedFalseAndTags_Id(Long id);
    Boolean existsByTitle(String title);
    Optional<NoteEntity> findByTitle(String title);
    List<NoteEntity> findByUserUsernameAndIsArchivedFalse(String username);
    List<NoteEntity> findByUserUsernameAndIsArchivedTrue(String username);

    List<NoteEntity> findByUserUsernameAndIsArchivedFalseAndTags_Id(String username, Long tagId);
}
