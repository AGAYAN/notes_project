package org.example.project_notes.Repository;

import org.example.project_notes.Entity.NoteEntity;
import org.example.project_notes.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository <NoteEntity, Long>  {
    List<NoteEntity> findByUser(UserEntity user);
    Optional<NoteEntity> findByIdAndUser(Long id, UserEntity user);
}
