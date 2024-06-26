package org.example.project_notes.Service;
import lombok.RequiredArgsConstructor;
import org.example.project_notes.Entity.NoteEntity;
import org.example.project_notes.Entity.UserEntity;
import org.example.project_notes.Repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final NoteRepository noteRepository;

    public List<NoteEntity> getHistoryForUser(UserEntity user) {
        return noteRepository.findByUser(user);
    }
}