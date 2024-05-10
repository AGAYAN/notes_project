package org.example.project_notes.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.project_notes.DTO.NoteDTO;
import org.example.project_notes.DTO.UserDTO;
import org.example.project_notes.Entity.NoteEntity;
import org.example.project_notes.Entity.UserEntity;
import org.example.project_notes.Service.AuthService;
import org.example.project_notes.Service.HistoryNotes;
import org.example.project_notes.Service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
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
    public String loginUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model, HttpServletRequest request) {
        try {
            UserEntity user = authService.loginUser(userDTO);
            request.getSession().setAttribute("user", user);
            return "redirect:/api/v1/notes/profile";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model, Principal principal) {
        if (principal != null) {
            UserEntity user = authService.getUserByEmail(principal.getName());
            model.addAttribute("user", user);
            return "/api/v1/notes/profile";
        } else {
            // Обработка случая, когда пользователь не аутентифицирован
            return "redirect:/api/v1/notes/login"; // Например, перенаправление на страницу входа
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
}


