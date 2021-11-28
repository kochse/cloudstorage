package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;

@Component
public class CredentialStorageService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    private String encodedKey;

    public CredentialStorageService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        this.encodedKey = Base64.getEncoder().encodeToString(key);
    }

    public Credential[] getCredentials(Integer userId) {
        Stream<Credential> credentials = Arrays.stream(this.credentialMapper.readAll(userId));
        return credentials.map(credential -> {
            if (credential.getPassword() != null) {
                //String password = encryptionService.decryptValue(credential.getPassword(), this.encodedKey);
                //credential.setPassword(password);
                credential.setKey(this.encodedKey);
            }
           return credential;
        }).toArray(Credential[]::new);
    }

    public void saveCredential(Credential credential) {
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), this.encodedKey);
        credential.setPassword(encryptedPassword);

        if (credential.getCredentialId() != null) {
            this.credentialMapper.update(credential);
        } else {
            this.credentialMapper.create(credential);
        }
    }

    public void deleteCredential(Integer credentialId) {
        this.credentialMapper.delete(credentialId);
    }
}
