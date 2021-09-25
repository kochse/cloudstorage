package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Component;

@Component
public class FileStorageService {

    private final FileMapper fileMapper;

    public FileStorageService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean isFilenameAvailable(String filename) {
        return fileMapper.read(filename) == null;
    }

    public File[] getFiles(Integer userId) {
        return this.fileMapper.readAll(userId);
    }

    public int uploadFile(File file) {
        return this.fileMapper.create(file);
    }
}
