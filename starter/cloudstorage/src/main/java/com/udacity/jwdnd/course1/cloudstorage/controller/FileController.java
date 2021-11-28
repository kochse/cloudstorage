package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class FileController {

    FileStorageService fileStorageService;
    UserService userService;

    public FileController(FileStorageService fileStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Principal principal, Model model, RedirectAttributes redirectAttrs) {

        User user = this.userService.getUser(principal.getName());

        if (!fileStorageService.isFilenameAvailable(file.getOriginalFilename())) {
            redirectAttrs.addFlashAttribute("filenameNotAvailable", true);
        } else {
            if (!file.isEmpty()) {
                try {
                    Integer result = fileStorageService.uploadFile(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()), user.getUserId(), file.getBytes()));
                    if (result == 1) {
                        redirectAttrs.addFlashAttribute("uploadFileSuccess", true);
                    }
                } catch (Exception ex) {
                    redirectAttrs.addFlashAttribute("uploadFileSuccess", false);
                }
            } else {
                redirectAttrs.addFlashAttribute("uploadFileSuccess", false);
            }
        }

        redirectAttrs.addFlashAttribute("selectFilesTab", true);
        return "redirect:/home";
    }

    @GetMapping("/deleteFile/{id}")
    public String deleteFile(@PathVariable Integer id, Principal principal, Model model, RedirectAttributes redirectAttrs) {
        this.fileStorageService.deleteFile(id);

        redirectAttrs.addFlashAttribute("selectFilesTab", true);
        return "redirect:/home";
    }

    @GetMapping("/viewFile/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable Integer id, Principal principal, Model model) {
        User user = this.userService.getUser(principal.getName());

        if (user != null) {
            ArrayList<File> files = new ArrayList<>(Arrays.asList(this.fileStorageService.getFiles(user.getUserId())));
            File file = files.stream().filter(a -> a.getFileId() == id).collect(Collectors.toList()).get(0);
            if (file != null) {
                byte[] fileData = file.getFileData();
                ByteArrayResource resource = new ByteArrayResource(fileData);

                HttpHeaders header = new HttpHeaders();
                header.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFileName() + "\"");

                return ResponseEntity.ok()
                        .headers(header)
                        .contentLength(fileData.length)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
        }

        return null;
    }
}
