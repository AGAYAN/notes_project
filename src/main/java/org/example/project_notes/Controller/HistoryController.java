package org.example.project_notes.Controller;

import org.example.project_notes.Entity.NoteEntity;
import org.example.project_notes.Entity.UserEntity;
import org.example.project_notes.Repository.UserRepository;
import org.example.project_notes.Service.HistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/notes")
public class HistoryController {
    private final UserRepository userRepository;
    private final HistoryService historyService;

    public HistoryController(UserRepository userRepository, HistoryService historyService) {
        this.userRepository = userRepository;
        this.historyService = historyService;
    }

    @GetMapping("/history")
    public String showHistoryForm(Model model) {
        model.addAttribute("email", "");
        return "history";
    }

    @PostMapping("/history")
    public String getNotes(@RequestParam("email") String email, Model model) {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            List<NoteEntity> notes = historyService.getHistoryForUser(user.get());
            model.addAttribute("notes", notes);
        }
        return "notes";
    }
}
