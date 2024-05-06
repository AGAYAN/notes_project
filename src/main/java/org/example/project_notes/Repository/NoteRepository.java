package org.example.project_notes.Repository;

import org.example.project_notes.Entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository <NoteEntity, Long>  {
}
