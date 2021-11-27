package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Component;

@Component
public class CredentialStorageService {

    private final CredentialMapper credentialMapper;

    public CredentialStorageService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public Credential[] getCredentials(Integer userId) {
        return this.credentialMapper.readAll(userId);
    }

    public void saveCredential(Credential credential) {
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
