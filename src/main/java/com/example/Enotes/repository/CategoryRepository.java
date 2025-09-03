package com.example.Enotes.repository;

import com.example.Enotes.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category,Integer> {

    List<Category> findByIsActiveTrue();

    Optional<Category> findByIdAndIsDeletedFalse(Integer id);

    List<Category> findByIsDeletedFalse();

    List<Category> findByIsActiveTrueAndIsDeletedFalse();
}
