package com.example.Enotes.repository;

import com.example.Enotes.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotesRepository extends JpaRepository<Notes,Integer> {
    Page<Notes> findByCreatedBy(Integer userId, Pageable pageable);
}
