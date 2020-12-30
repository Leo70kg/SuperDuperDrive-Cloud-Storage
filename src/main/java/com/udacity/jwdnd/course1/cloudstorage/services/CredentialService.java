package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    private String generateEncodeKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);

    }
    private String encodePassword(String password, String encodeKey) {
        return encryptionService.encryptValue(password, encodeKey);

    }

    public void add(Credential credential, Integer userId) {
        credential.setUserId(userId);
        String encodeKey = generateEncodeKey();
        String encodePass = encodePassword(credential.getPassword(), encodeKey);
        credential.setKey(encodeKey);
        credential.setPassword(encodePass);

        credentialMapper.insert(credential);
    }

    public void update(Credential credential, Integer userId) {

        String encodeKey = generateEncodeKey();
        String encodePass = encodePassword(credential.getPassword(), encodeKey);
        credential.setKey(encodeKey);
        credential.setPassword(encodePass);

        credentialMapper.update(credential);

    }

    public void delete(Integer credentialId) {
        credentialMapper.delete(credentialId);
    }

    public List<Credential> findByUserId(Integer userId) {
        return credentialMapper.findByUserId(userId);
    }
}
