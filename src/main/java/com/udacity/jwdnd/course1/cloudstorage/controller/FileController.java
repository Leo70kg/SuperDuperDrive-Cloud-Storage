package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import com.udacity.jwdnd.course1.cloudstorage.services.UserFileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


@Controller
public class FileController {

    private final UserService userService;
    private final UserFileService userFileService;

    public FileController(UserService userService, UserFileService userFileService) {
        this.userService = userService;
        this.userFileService = userFileService;
    }

    public Integer getUserId(Authentication authentication) {

        return this.userService.getUser(authentication.getName()).getUserId();
    }

    @PostMapping("/file-upload")
    public String handleFileUploadAndEdit(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication ){
        if(fileUpload.isEmpty()){
            System.out.println("Empty file");
            model.addAttribute("error", true);
            model.addAttribute("message", "Empty file selected or there is no file which is selected");
            return "result";
        }

        Integer userId = this.getUserId(authentication);

        if(userFileService.filenameExists(fileUpload.getOriginalFilename(), userId)) {
            model.addAttribute("error",true);
            model.addAttribute("message","File with that name already exists");
            return "result";
        }
        try {
            userFileService.store(fileUpload, userId);
            model.addAttribute("success",true);
            model.addAttribute("message","New File added successfully!");
        }catch (Exception e) {
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
        }

        return "result";
    }

    @GetMapping("/file-view/{fileId}")
    public void viewFile(@PathVariable("fileId") Integer fileId, String openStyle,
                         HttpServletResponse response) throws IOException {

        openStyle = openStyle == null ? "inline" : openStyle;

        UserFile userFile = userFileService.findByFileId(fileId);

        response.setHeader("content-disposition", openStyle + ";fileName=" +
                URLEncoder.encode(userFile.getFileName(), "UTF-8"));

        ServletOutputStream os = response.getOutputStream();
        os.write(userFile.getFileData());

        IOUtils.closeQuietly(os);
    }

    @GetMapping("/file-download/{fileId}")
    public void downloadFile(@PathVariable("fileId") Integer fileId, String openStyle,
                             HttpServletResponse response) throws IOException {

        openStyle = openStyle == null ? "attachment" : openStyle;

        UserFile userFile = userFileService.findByFileId(fileId);

        response.setHeader("content-disposition", openStyle + ";fileName=" +
                URLEncoder.encode(userFile.getFileName(), "UTF-8"));

        ServletOutputStream os = response.getOutputStream();
        os.write(userFile.getFileData());

        IOUtils.closeQuietly(os);
    }

    @GetMapping("/file-delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") Integer fileId, Model model) {

        try {
            userFileService.delete(fileId);
            model.addAttribute("success", true);
            model.addAttribute("message", "Delete file successfully!");
        }catch (Exception e) {
            model.addAttribute("error",true);
            model.addAttribute("message",e.getMessage());
        }

        return "result";
    }
}
