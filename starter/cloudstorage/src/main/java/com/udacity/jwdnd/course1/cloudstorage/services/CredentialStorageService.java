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

    public int saveCredential(Credential credential) {
        return this.credentialMapper.create(credential);
    }

}