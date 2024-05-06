package org.example.project_notes.Controller;

import org.example.project_notes.DTO.NoteDTO;
import org.example.project_notes.DTO.UserDTO;
import org.example.project_notes.Entity.NoteEntity;
import org.example.project_notes.Service.AuthService;
import org.example.project_notes.Service.HistoryNotes;
import org.example.project_notes.Service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/api/v1/notes")
public class AuthController {
    private final AuthService authService;
    private final NoteService noteService;

    private final HistoryNotes historyNotes;

    public AuthController(AuthService authService, NoteService noteService, HistoryNotes historyNotes) {
        this.historyNotes = historyNotes;
        this.noteService = noteService;
        this.authService = authService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        try {
            authService.registerUser(userDTO);
            return "redirect:/api/v1/notes/login";
        }
        catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        try {
            if (authService.loginUser(userDTO)) {
                return "redirect:/api/v1/notes/protected"; // Перенаправляем на защищенную страницу при успешной аутентификации
            } else {
                model.addAttribute("error", "Invalid email or password");
                return "login"; // Возвращаем на страницу входа с сообщением об ошибке
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login"; // Возвращаем на страницу входа с сообщением об ошибке
        }
    }


    @GetMapping("/protected")
    public String showNoteForm(Model model) {
        model.addAttribute("noteDTO", new NoteDTO());
        return "protected"; // Предполагается, что у вас есть шаблон с именем "noteForm"
    }

    @PostMapping("/protected")
    public String createNote(NoteDTO noteDTO, Model model) {
        model.addAttribute("title", noteDTO.getTitle());
        model.addAttribute("content", noteDTO.getContent());
        model.addAttribute("email", noteDTO.getEmail());
        noteService.saveNote(noteDTO.getTitle(), noteDTO.getContent(), noteDTO.getEmail());
        return "protected"; // Перенаправление на главную страницу
    }

    @GetMapping("/history")
    public String getNotes(Model model) {
        List<NoteEntity> user = historyNotes.getHistoryNotes();
        model.addAttribute("user", user);
        return "history";
    }
}


