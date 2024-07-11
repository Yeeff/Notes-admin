package com.horizonx.test.config;

import com.horizonx.test.domain.entities.NoteEntity;
import com.horizonx.test.domain.entities.TagEntity;
import com.horizonx.test.repositories.NoteRepository;
import com.horizonx.test.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;

@Configuration
@Profile({"test","dev"})
public class DevDatabaseInitializer implements CommandLineRunner {
    private final NoteRepository repo;
    private  final TagRepository tagRepo;

    public DevDatabaseInitializer(NoteRepository repo, TagRepository tagRepo) {
        this.repo = repo;
        this.tagRepo = tagRepo;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        NoteEntity note1;
        NoteEntity note2;
        NoteEntity note3;
        NoteEntity note4;
        NoteEntity note5;

        TagEntity tag1;
        TagEntity tag2;

        note1 = repo.findByTitle("Meeting Notes")
                .orElse(
                        repo.save(
                                NoteEntity.builder()
                                        .title("Meeting Notes")
                                        .tags(new HashSet<>())
                                        .content( "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ut libero eu ligula consequat volutpat. Nullam malesuada egestas ligula, in convallis dolor consectetur non. Praesent id nisi nec libero pretium volutpat. Nulla facilisi. Sed at lacus sit amet nulla sagittis cursus. Vestibulum gravida felis ac venenatis vulputate. Vivamus condimentum. ")
                                        .isArchived(false)
                                        .build()
                        )
                );

        if (!repo.existsByTitle("Meeting Notes")){
            note1 = NoteEntity.builder()
                    .title("Meeting Notes")
                    .content( "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ut libero eu ligula consequat volutpat. Nullam malesuada egestas ligula, in convallis dolor consectetur non. Praesent id nisi nec libero pretium volutpat. Nulla facilisi. Sed at lacus sit amet nulla sagittis cursus. Vestibulum gravida felis ac venenatis vulputate. Vivamus condimentum. ")
                    .isArchived(false)
                    .build();
            note1 = repo.save(note1);
        }
        if (!repo.existsByTitle("Project Plan")){
            note2 = NoteEntity.builder()
                    .title("Project Plan")
                    .content(  "Nunc ut turpis ut orci suscipit malesuada. Phasellus egestas, mauris vel egestas dictum, lectus libero faucibus turpis, id cursus enim eros in tortor. Nam tempor neque sit amet felis vehicula, vel convallis nisi pretium. Aenean venenatis ligula ut purus pretium ultricies. Maecenas sagittis felis et ligula tristique, sed dictum lectus tincidunt. , eget dictum magna")
                    .isArchived(false)
                    .build();
            note2= repo.save(note2);
        }
        if (!repo.existsByTitle("Research Summary")){
            note3 = NoteEntity.builder()
                    .title("Research Summary")
                    .content("Aliquam erat volutpat. Ut vehicula, urna at vehicula dapibus, arcu urna efficitur eros, vel convallis mauris felis sit amet mauris. Integer vestibulum neque nec lectus bibendum, nec pharetra lorem interdum. Cras et lacinia libero. Sed quis consequat odio. Donec ultricies nunc sed nulla feugiat convallis. Nullam fermentum velit id metus dignissim, ")
                    .isArchived(false)
                    .build();
            note3 = repo.save(note3);
        }
        if (!repo.existsByTitle("Daily Journal")){
            note4 = NoteEntity.builder()
                    .title("Daily Journal")
                    .content("Suspendisse potenti. Fusce iaculis eros ac lorem posuere, in dictum justo posuere. Vestibulum sollicitudin, ligula nec consequat venenatis, lorem libero sagittis risus, eu iaculis nulla magna in arcu. Integer interdum lorem ac purus viverra, a auctor nisi tincidunt. ")
                    .isArchived(false)
                    .build();
            note4 = repo.save(note4);
        }
        if (!repo.existsByTitle("Workshop Agenda")){
            note5 = NoteEntity.builder()
                    .title("Workshop Agenda")
                    .content("Vivamus sit amet lorem at nulla ullamcorper dignissim. Sed convallis suscipit libero. Etiam ac odio at magna convallis lacinia a at nunc. Fusce consectetur feugiat mauris nec convallis. Nulla facilisi. Vestibulum fringilla feugiat justo, et dignissim urna pulvinar at. Phasellus nec volutpat leo. Sed eget dui dolor. Etiam luctus, ")
                    .isArchived(false)
                    .build();
            repo.save(note5);
        }


        tag1 = tagRepo.findByName("Job")
                        .orElse(
                                tagRepo.save(
                                        TagEntity.builder()
                                                .notes(new HashSet<>())
                                                .name("Job")
                                                .build()
                                )
                        );

        tag1 = tagRepo.findByName("Study")
                .orElse(
                        tagRepo.save(
                                TagEntity.builder()
                                        .notes(new HashSet<>())
                                        .name("Study")
                                        .build()
                        )
                );

    }
}
