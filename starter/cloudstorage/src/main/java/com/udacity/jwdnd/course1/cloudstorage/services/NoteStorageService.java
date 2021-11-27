package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteStorageService {

    private final NoteMapper noteMapper;

    public NoteStorageService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note[] getNotes(Integer userId) {
        return this.noteMapper.readAll(userId);
    }

    public int saveNote(Note note) {
        if (note.getNoteId() != null) {
            return this.noteMapper.update(note);
        } else {
            return this.noteMapper.create(note);
        }
    }



    public void deleteNote(Integer noteId) {
        this.noteMapper.delete(noteId);
    }
}
