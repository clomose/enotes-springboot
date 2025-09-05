package com.example.Enotes.repository;

import com.example.Enotes.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
}
