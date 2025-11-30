package com.example.auth.Service;

import com.example.auth.Entity.Note;
import com.example.auth.Repository.NoteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepo;
    

    @Transactional
    public void createNote(String title, String note, Long userId) {
        noteRepo.saveNote(title, note, userId);
    }

    public List<Note> getNotes(Long userId) {
        return noteRepo.findNotesByUserId(userId);
    }

    @Transactional
    public boolean updateNote(Long id, Long userId, String title, String note) {
        return noteRepo.updateNote(id, userId, title, note) > 0;
    }

    @Transactional
    public boolean deleteNote(Long id, Long userId) {
        return noteRepo.deleteNote(id, userId) > 0;
    }
}

