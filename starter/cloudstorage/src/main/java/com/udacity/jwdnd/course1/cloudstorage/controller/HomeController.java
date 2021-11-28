package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class HomeController {

    FileStorageService fileStorageService;
    CredentialStorageService credentialStorageService;
    NoteStorageService noteStorageService;
    UserService userService;

    public HomeController(FileStorageService fileStorageService, CredentialStorageService credentialStorageService, NoteStorageService noteStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.credentialStorageService = credentialStorageService;
        this.noteStorageService = noteStorageService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirect(Principal principal, RedirectAttributes redirectAttrs) {
        if (principal == null) {
            return "redirect:/login";
        }

        redirectAttrs.addFlashAttribute("selectFilesTab", true);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Principal principal, Model model) {

        User user = this.userService.getUser(principal.getName());
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("files", this.fileStorageService.getFiles(user.getUserId()));
        model.addAttribute("notes", this.noteStorageService.getNotes(user.getUserId()));
        model.addAttribute("credentials", this.credentialStorageService.getCredentials(user.getUserId()));

        Object showNotes = model.asMap().get("selectNotesTab");
        Object showCredentials = model.asMap().get("selectCredentialsTab");

        if (showNotes == null && showCredentials == null) {
            model.addAttribute("selectFilesTab", true);
        }

        return "home";
    }




}
