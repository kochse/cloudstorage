package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class NoteController {

    NoteStorageService noteStorageService;
    UserService userService;

    public NoteController(NoteStorageService noteStorageService, UserService userService) {
        this.noteStorageService = noteStorageService;
        this.userService = userService;
    }

    @PostMapping("/saveNote")
    public String addNote(Note note, Principal principal, Model model, RedirectAttributes redirectAttrs) {
        User user = this.userService.getUser(principal.getName());
        this.noteStorageService.saveNote(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), user.getUserId()));
        redirectAttrs.addFlashAttribute("addNoteSuccess", true);
        redirectAttrs.addFlashAttribute("selectNotesTab", true);
        redirectAttrs.addFlashAttribute("selectFilesTab", false);
        redirectAttrs.addFlashAttribute("selectCredentialsTab", false);

        return "redirect:/home";
    }

    @GetMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable Integer id, Principal principal, Model model, RedirectAttributes redirectAttrs) {
        if (principal == null) {
            return "redirect:/login";
        }

        redirectAttrs.addFlashAttribute("selectNotesTab", true);
        redirectAttrs.addFlashAttribute("selectFilesTab", false);
        redirectAttrs.addFlashAttribute("selectCredentialsTab", false);

        this.noteStorageService.deleteNote(id);
        return "redirect:/home";
    }
}
