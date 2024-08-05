package com.dev2prod.demo.config;

import com.dev2prod.demo.domain.entities.*;
import com.dev2prod.demo.domain.entities.enums.RoleEnum;
import com.dev2prod.demo.repositories.NoteRepository;
import com.dev2prod.demo.repositories.TagRepository;
import com.dev2prod.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@Profile("dev")
public class DevDatabaseInitializer implements CommandLineRunner {
    private final NoteRepository repo;
    private  final TagRepository tagRepo;

    private final UserRepository userRepository;

    public DevDatabaseInitializer(NoteRepository repo, TagRepository tagRepo, UserRepository userRepository) {
        this.repo = repo;
        this.tagRepo = tagRepo;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        /*  PERMISSIONS   */
        PermissionEntity createPermission = PermissionEntity.builder()
                .name("CREATE")
                .build();

        PermissionEntity readPermission = PermissionEntity.builder()
                .name("READ")
                .build();

        PermissionEntity updatePermission = PermissionEntity.builder()
                .name("UPDATE")
                .build();

        PermissionEntity deletePermission = PermissionEntity.builder()
                .name("DELETE")
                .build();

        PermissionEntity refactorPermission = PermissionEntity.builder()
                .name("REFACTOR")
                .build();

        /*  ROLES  */
        RoleEntity roleAdmin = RoleEntity.builder()
                .roleEnum(RoleEnum.ADMIN)
                .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                .build();

        RoleEntity roleUser = RoleEntity.builder()
                .roleEnum(RoleEnum.USER)
                .permissionList(Set.of(createPermission, readPermission))
                .build();

        RoleEntity roleInvited = RoleEntity.builder()
                .roleEnum(RoleEnum.INVITED)
                .permissionList(Set.of(readPermission))
                .build();

        RoleEntity roleDeveloper = RoleEntity.builder()
                .roleEnum(RoleEnum.DEVELOPER)
                .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                .build();

        /*  USERS  */
        UserEntity userSantiago = UserEntity.builder()
                .username("santiago")
                .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleAdmin))
                .build();

        UserEntity userDaniel = UserEntity.builder()
                .username("daniel")
                .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleUser))
                .build();

        UserEntity userAndrea = UserEntity.builder()
                .username("andrea")
                .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleInvited))
                .build();

        UserEntity userAnyi = UserEntity.builder()
                .username("anyi")
                .password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(Set.of(roleDeveloper))
                .build();

        userRepository.saveAll(List.of(userSantiago, userDaniel, userAndrea, userAnyi));

        /*  NOTES  */
        NoteEntity note1;
        NoteEntity note2;
        NoteEntity note3;
        NoteEntity note4;
        NoteEntity note5;

        TagEntity tag1;
        TagEntity tag2;
        TagEntity tag3;

        note1 = repo.findByTitle("Meeting Notes")
                .orElse(
                        repo.save(
                                NoteEntity.builder()
                                        .user(userSantiago)
                                        .title("Meeting Notes")
                                        .tags(new HashSet<>())
                                        .content( "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ut libero eu ligula consequat volutpat. Nullam malesuada egestas ligula, in convallis dolor consectetur non. Praesent id nisi nec libero pretium volutpat. Nulla facilisi. Sed at lacus sit amet nulla sagittis cursus. Vestibulum gravida felis ac venenatis vulputate. Vivamus condimentum. ")
                                        .isArchived(false)
                                        .build()
                        )
                );

        if (!repo.existsByTitle("Meeting Notes")){
            note1 = NoteEntity.builder()
                    .user(userSantiago)
                    .title("Meeting Notes")
                    .content( "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ut libero eu ligula consequat volutpat. Nullam malesuada egestas ligula, in convallis dolor consectetur non. Praesent id nisi nec libero pretium volutpat. Nulla facilisi. Sed at lacus sit amet nulla sagittis cursus. Vestibulum gravida felis ac venenatis vulputate. Vivamus condimentum. ")
                    .isArchived(false)
                    .build();
            note1 = repo.save(note1);
        }
        if (!repo.existsByTitle("Project Plan")){
            note2 = NoteEntity.builder()
                    .user(userSantiago)
                    .title("Project Plan")
                    .content(  "Nunc ut turpis ut orci suscipit malesuada. Phasellus egestas, mauris vel egestas dictum, lectus libero faucibus turpis, id cursus enim eros in tortor. Nam tempor neque sit amet felis vehicula, vel convallis nisi pretium. Aenean venenatis ligula ut purus pretium ultricies. Maecenas sagittis felis et ligula tristique, sed dictum lectus tincidunt. , eget dictum magna")
                    .isArchived(false)
                    .build();
            note2= repo.save(note2);
        }
        if (!repo.existsByTitle("Research Summary")){
            note3 = NoteEntity.builder()
                    .user(userDaniel)
                    .title("Research Summary")
                    .content("Aliquam erat volutpat. Ut vehicula, urna at vehicula dapibus, arcu urna efficitur eros, vel convallis mauris felis sit amet mauris. Integer vestibulum neque nec lectus bibendum, nec pharetra lorem interdum. Cras et lacinia libero. Sed quis consequat odio. Donec ultricies nunc sed nulla feugiat convallis. Nullam fermentum velit id metus dignissim, ")
                    .isArchived(false)
                    .build();
            note3 = repo.save(note3);
        }
        if (!repo.existsByTitle("Daily Journal")){
            note4 = NoteEntity.builder()
                    .user(userDaniel)
                    .title("Daily Journal")
                    .content("Suspendisse potenti. Fusce iaculis eros ac lorem posuere, in dictum justo posuere. Vestibulum sollicitudin, ligula nec consequat venenatis, lorem libero sagittis risus, eu iaculis nulla magna in arcu. Integer interdum lorem ac purus viverra, a auctor nisi tincidunt. ")
                    .isArchived(false)
                    .build();
            note4 = repo.save(note4);
        }
        if (!repo.existsByTitle("Workshop Agenda")){
            note5 = NoteEntity.builder()
                    .user(userDaniel)
                    .title("Workshop Agenda")
                    .content("Vivamus sit amet lorem at nulla ullamcorper dignissim. Sed convallis suscipit libero. Etiam ac odio at magna convallis lacinia a at nunc. Fusce consectetur feugiat mauris nec convallis. Nulla facilisi. Vestibulum fringilla feugiat justo, et dignissim urna pulvinar at. Phasellus nec volutpat leo. Sed eget dui dolor. Etiam luctus, ")
                    .isArchived(false)
                    .build();
            repo.save(note5);
        }

        /*  TAGS   */
        tag1= new TagEntity();
        tag1.setName("tag1");

        tag2= new TagEntity();
        tag2.setName("tag2");

        tag3= new TagEntity();
        tag3.setName("tag3");

        tagRepo.saveAll(List.of(tag1, tag2, tag3));

    }
}
