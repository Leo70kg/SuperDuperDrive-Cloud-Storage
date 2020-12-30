package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void add(Note note, Integer userId) {
        note.setUserId(userId);
        noteMapper.insert(note);
    }

    public void update(Note note) {
        noteMapper.update(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription());

    }

    public void delete(Integer fileId) {
        noteMapper.delete(fileId);
    }

    public List<Note> findByUserId(Integer userId) {
        return noteMapper.findByUserId(userId);
    }
}
