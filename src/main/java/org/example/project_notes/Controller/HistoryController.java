package org.example.project_notes.Controller;

import org.example.project_notes.Entity.NoteEntity;
import org.example.project_notes.Entity.UserEntity;
import org.example.project_notes.Repository.NoteRepository;
import org.example.project_notes.Repository.UserRepository;
import org.example.project_notes.Service.HistoryNotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/notes")
public class HistoryController {

    private UserRepository userRepository;

    public HistoryController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/history")
    public String showHistoryForm(Model model) {
        model.addAttribute("email", "");
        return "history";
    }

    @PostMapping("/history")
    public String getNotes(@RequestParam("email") String email, Model model) {
        // Находим пользователей по email
        Optional<UserEntity> users = userRepository.findByEmail(email);

        // Получаем список заметок для каждого пользователя
        List<NoteEntity> notes = users.stream()
                .flatMap(user -> user.getNotes().stream())
                .collect(Collectors.toList());

        // Передаем список заметок в модель для отображения
        model.addAttribute("notes", notes);

        return "notes";
    }
}
