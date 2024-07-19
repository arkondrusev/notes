package com.example.notes.repository;

import com.example.notes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepositoryJPA  extends JpaRepository<Note, Integer> {


    
}
