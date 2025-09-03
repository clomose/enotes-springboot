package com.example.Enotes.repository;

import com.example.Enotes.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category,Integer> {

    List<Category> findByIsActiveTrue();
}
