package com.dev2prod.demo.services;

import com.dev2prod.demo.domain.entities.TagEntity;
import com.dev2prod.demo.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepo;
    public TagService(TagRepository tagRepo) {
        this.tagRepo = tagRepo;
    }

    public void saveProject(TagEntity projectObj) {
        tagRepo.save(projectObj);
    }

    public List<TagEntity> getProjectDetails(Long projectId) {
            return tagRepo.findAll();

    }

    public void deleteProject(Long projectId) {
        tagRepo.deleteById(projectId);
    }

    //---
    public List<TagEntity> getAll(){
        return tagRepo.findAll();
    }
}
