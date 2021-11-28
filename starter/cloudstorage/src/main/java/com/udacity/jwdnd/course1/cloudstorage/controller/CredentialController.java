package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class CredentialController {

    CredentialStorageService credentialStorageService;
    UserService userService;

    public CredentialController(CredentialStorageService credentialStorageService, UserService userService) {
        this.credentialStorageService = credentialStorageService;
        this.userService = userService;
    }

    @PostMapping("/saveCredential")
    public String addCredential(Credential credential, Principal principal, Model model, RedirectAttributes redirectAttrs) {
        User user = this.userService.getUser(principal.getName());
        if (user != null) {
            this.credentialStorageService.saveCredential(new Credential(credential.getCredentialId(), credential.getUrl(), credential.getUserName(), credential.getKey(), credential.getPassword(), user.getUserId()));
            redirectAttrs.addFlashAttribute("addCredentialSuccess", true);
        }

        redirectAttrs.addFlashAttribute("selectCredentialsTab", true);
        return "redirect:/home";
    }

    @GetMapping("/deleteCredential/{id}")
    public String deleteCredential(@PathVariable Integer id, Principal principal, Model model, RedirectAttributes redirectAttrs) {
        if (principal == null) {
            return "redirect:/login";
        }

        redirectAttrs.addFlashAttribute("selectCredentialsTab", true);
        this.credentialStorageService.deleteCredential(id);
        return "redirect:/home";
    }
}
