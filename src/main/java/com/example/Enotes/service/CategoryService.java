package com.example.Enotes.service;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.dto.CategoryResponse;
import com.example.Enotes.entity.Category;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(CategoryDto categoryDto);

    public List<CategoryDto> getAllCategory();

    List<CategoryResponse> getActiveCategory();
}
