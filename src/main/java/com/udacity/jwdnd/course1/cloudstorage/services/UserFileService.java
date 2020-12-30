package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserFileService {
    private final UserFileMapper userFileMapper;

    public UserFileService(UserFileMapper userFileMapper) {
        this.userFileMapper = userFileMapper;
    }

    public void store(MultipartFile file, Integer userId) throws IOException {

        if (file.isEmpty()) {
            System.out.println("error");
            throw new StorageException("Failed to store empty file.");
        }

        Long size = file.getSize();
        String fileSize = size.toString();

        UserFile userFile = new UserFile();
        userFile.setFileName(file.getOriginalFilename());
        userFile.setContentType(file.getContentType());
        userFile.setFileSize(fileSize);
        userFile.setUserId(userId);
        userFile.setFileData(file.getBytes());

        userFileMapper.insert(userFile);

    }

    public void delete(Integer fileId) {
        userFileMapper.delete(fileId);
    }

    public UserFile findByFileId(Integer fileId) {
        return userFileMapper.findByFileId(fileId);
    }

    public boolean filenameExists(String fileName, Integer userId) {

        return !userFileMapper.findByFileName(fileName, userId).isEmpty();
    }

    public List<UserFile> findByUserId(Integer userId) {
        return userFileMapper.findByUserId(userId);
    }


}
