package com.example.auth.Repository;

import com.example.auth.Entity.Note;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    
    @Modifying
    @Query(value = "INSERT INTO note (title, note, user_id) VALUES (:title, :note, :userId)", nativeQuery = true)
    void saveNote(@Param("title") String title,
                  @Param("note") String note,
                  @Param("userId") Long userId);

    
    @Query("SELECT n FROM Note n WHERE n.user.id = :userId")
    List<Note> findNotesByUserId(@Param("userId") Long userId);

    
    @Modifying
    @Query("UPDATE Note n SET n.title = :title, n.note = :note WHERE n.id = :id AND n.user.id = :userId")
    int updateNote(@Param("id") Long id,
                   @Param("userId") Long userId,
                   @Param("title") String title,
                   @Param("note") String note);


    @Modifying
    @Query("DELETE FROM Note n WHERE n.id = :id AND n.user.id = :userId")
    int deleteNote(@Param("id") Long id, @Param("userId") Long userId);
}
