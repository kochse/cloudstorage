package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
public class HomeController {

    FileStorageService fileStorageService;
    CredentialStorageService credentialStorageService;
    NoteStorageService noteStorageService;
    UserService userService;

    // Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(FileStorageService fileStorageService, CredentialStorageService credentialStorageService, NoteStorageService noteStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.credentialStorageService = credentialStorageService;
        this.noteStorageService = noteStorageService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirect(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        // logger.trace("A TRACE Message");
        User user = this.userService.getUser(principal.getName());
        model.addAttribute("files", this.fileStorageService.getFiles(user.getUserId()));
        model.addAttribute("notes", this.noteStorageService.getNotes(user.getUserId()));
        model.addAttribute("credentials", this.credentialStorageService.getCredentials(user.getUserId()));

        return "home";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = this.userService.getUser(principal.getName());

        if (!fileStorageService.isFilenameAvailable(file.getOriginalFilename())) {
            model.addAttribute("filenameNotAvailable", true);
        } else {
            if (!file.isEmpty()) {
                try {
                    Integer result = fileStorageService.uploadFile(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), user.getUserId(), file.getBytes()));
                    if (result == 1) {
                        model.addAttribute("uploadSuccess", true);
                    }
                } catch (Exception ex) {
                    model.addAttribute("uploadSuccess", false);
                }
            } else {
                model.addAttribute("uploadSuccess", false);
            }
        }



        return "redirect:/home";
    }

    @PostMapping("/deleteFile")
    public String deleteFile(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        return "redirect:/home";
    }

    @PostMapping("/saveNote")
    public String addNote(Note note, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = this.userService.getUser(principal.getName());

        this.noteStorageService.saveNote(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), user.getUserId()));

        return "redirect:/home";
    }

    @PostMapping("/saveCredential")
    public String addCredential(Credential credential, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = this.userService.getUser(principal.getName());

        this.credentialStorageService.saveCredential(new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUserName(), credential.getKey(), credential.getPassword(), user.getUserId()));

        return "redirect:/home";
    }
}
