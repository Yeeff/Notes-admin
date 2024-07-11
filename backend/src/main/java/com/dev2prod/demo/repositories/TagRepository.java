package com.dev2prod.demo.repositories;

import com.dev2prod.demo.domain.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

}
