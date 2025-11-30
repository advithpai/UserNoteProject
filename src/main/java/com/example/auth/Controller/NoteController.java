package com.example.auth.Controller;

import com.example.auth.Entity.Note;
import com.example.auth.Repository.UserRepository;
import com.example.auth.Service.JwtService;
import com.example.auth.Service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;
    private final JwtService jwtService;
    private final UserRepository userRepo;

   private Long getUserIdFromJwt(HttpServletRequest request) {
    String auth = request.getHeader("Authorization");
    String token = auth.substring(7);
    return jwtService.extractUserId(token, userRepo); 
}


    @PostMapping("/add")
    public ResponseEntity<?> addNote(@RequestBody Note note, HttpServletRequest request) {
        Long userId = getUserIdFromJwt(request);
        noteService.createNote(note.getTitle(), note.getNote(), userId);
        return ResponseEntity.ok("Note Added Successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getNotes(HttpServletRequest request) {
        Long userId = getUserIdFromJwt(request);
        List<Note> notes = noteService.getNotes(userId);
        return ResponseEntity.ok(notes);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNote(@PathVariable Long id,
                                        @RequestBody Note note,
                                        HttpServletRequest request) {
        Long userId = getUserIdFromJwt(request);
        boolean ok = noteService.updateNote(id, userId, note.getTitle(), note.getNote());
        return ok ? ResponseEntity.ok("Updated!") :
                ResponseEntity.status(403).body("Not Allowed");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id,
                                        HttpServletRequest request) {
        Long userId = getUserIdFromJwt(request);
        boolean ok = noteService.deleteNote(id, userId);
        return ok ? ResponseEntity.ok("Deleted!") :
                ResponseEntity.status(403).body("Not Allowed");
    }
}
