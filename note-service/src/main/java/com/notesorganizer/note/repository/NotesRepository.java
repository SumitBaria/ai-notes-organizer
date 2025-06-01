package com.notesorganizer.note.repository;

import com.notesorganizer.note.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotesRepository extends JpaRepository<Notes, UUID> {

    List<Notes> findByUserId(UUID userId);

    @Transactional
    void deleteById(UUID id);
}
