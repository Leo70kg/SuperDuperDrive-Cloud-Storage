package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserFileService userFileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private List<Note> notes;
    private List<UserFile> files;
    private List<Credential> credentials;


    public HomeController(UserFileService userFileService, UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userFileService = userFileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @PostConstruct
    public void postConstruct(){
        credentials = new ArrayList<>();
        notes = new ArrayList<>();
        files = new ArrayList<>();

    }

    @ModelAttribute("encryptionService")
    public EncryptionService getEncryptionService(){
        return new EncryptionService();
    }

    @ModelAttribute("credential")
    public CredentialForm getCredentialsForm(){
        return new CredentialForm();
    }

    @GetMapping()
    public String homeView(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());

        if(user == null) {
            return "home";
        }
        notes = noteService.findByUserId(user.getUserId());
        credentials = credentialService.findByUserId(user.getUserId());
        files = userFileService.findByUserId(user.getUserId());

        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);

        return "home";
    }


}
