package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    private Credential convertToCredential(CredentialForm credentialForm, Integer userId){
        Credential credential = new Credential();
        credential.setUserId(userId);
        credential.setCredentialId(credentialForm.getCredentialId());
        credential.setUrl(credentialForm.getUrl());
        credential.setUsername(credentialForm.getUsername());
        credential.setPassword(credentialForm.getPassword());
        return credential;
    }

    private Integer getUserId(Authentication authentication) {

        return this.userService.getUser(authentication.getName()).getUserId();
    }

    @PostMapping("/credential")
    public String handleCredentialUploadAndEdit(Model model, @ModelAttribute("credential") CredentialForm credentialForm,
                                                Authentication authentication, @ModelAttribute("encryptionService") EncryptionService encryptionService){

        Integer userId = this.getUserId(authentication);
        Credential credential = convertToCredential(credentialForm, userId);

        try {
            if (credential.getCredentialId() == null) {
                credentialService.add(credential, userId);
                model.addAttribute("message", "Add Credential successfully!");
            }
            else {
                credentialService.update(credential, userId);
                model.addAttribute("message","Update Credential successfully!");
            }
            model.addAttribute("success",true);

        } catch(Exception e){
            System.out.println("error: "+e.getMessage());

            model.addAttribute("error",true);
            model.addAttribute("message",  e.getMessage());
        }

        return "result";
    }

    @GetMapping("/credential-delete/{credentialId}")
    public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Model model) {

        try {
            credentialService.delete(credentialId);
            model.addAttribute("success", true);
            model.addAttribute("message", "Delete Credential successfully!");
        }catch (Exception e) {
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
        }

        return "result";
    }
}
