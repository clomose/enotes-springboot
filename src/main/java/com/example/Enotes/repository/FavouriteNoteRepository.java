package com.example.Enotes.repository;

import com.example.Enotes.entity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteNoteRepository extends JpaRepository<FavouriteNote,Integer> {
    List<FavouriteNote> findByUserId(int userId);
}
