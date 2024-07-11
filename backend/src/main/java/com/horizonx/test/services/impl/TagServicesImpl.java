package com.horizonx.test.services.impl;

import com.horizonx.test.domain.entities.TagEntity;
import com.horizonx.test.repositories.NoteRepository;
import com.horizonx.test.repositories.TagRepository;
import com.horizonx.test.services.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServicesImpl implements TagService {

    private TagRepository tagRepo;

    public TagServicesImpl(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    public List<TagEntity> getAll(){
        return tagRepo.findAll();
    }

}
