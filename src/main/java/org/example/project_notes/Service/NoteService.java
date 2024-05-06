package org.example.project_notes.Service;

import lombok.RequiredArgsConstructor;
import org.example.project_notes.Entity.NoteEntity;
import org.example.project_notes.Entity.UserEntity;
import org.example.project_notes.Repository.NoteRepository;
import org.example.project_notes.Repository.UserRepository;
import org.example.project_notes.enumType.ErrorCode;
import org.example.project_notes.exceptions.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final MailService mailService;
    private final UserRepository userRepository;

    @Transactional
    public void saveNote(String title, String content, String email) {
        if (title.isEmpty() || content.isEmpty()) {
            throw new BadRequestException(ErrorCode.TITLE_IS_EMPTY);
        }

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(ErrorCode.USER_NOT_FOUND));

        NoteEntity note = NoteEntity.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();

        noteRepository.save(note);
        mailService.sendPasswordResetEmail(email);
    }

}