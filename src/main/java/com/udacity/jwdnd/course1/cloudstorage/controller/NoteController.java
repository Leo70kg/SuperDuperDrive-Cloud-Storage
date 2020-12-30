package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    public Integer getUserId(Authentication authentication) {

        return this.userService.getUser(authentication.getName()).getUserId();
    }

    @PostMapping("/note")
    public String addNote(Note note, Model model, Authentication authentication){

        Integer userId = this.getUserId(authentication);

        if (note.getNoteId() != null) {
            try {
                noteService.update(note);
                model.addAttribute("success",true);
                model.addAttribute("message","Update Note successfully!");
            }catch (Exception e) {
                model.addAttribute("error",true);
                model.addAttribute("message",e.getMessage());
            }
            return "result";
        }

        try {
            noteService.add(note, userId);
            model.addAttribute("success",true);
            model.addAttribute("message","New Note added successfully!");
        }catch (Exception e) {
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
        }

        return "result";
    }

    @GetMapping("/note-delete/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer fileId, Model model) {

        try {
            noteService.delete(fileId);
            model.addAttribute("success", true);
            model.addAttribute("message", "Delete note successfully!");
        }catch (Exception e) {
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
        }

        return "result";
    }

}
