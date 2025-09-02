package com.example.Enotes.service;

import com.example.Enotes.entity.Category;

import java.util.List;

public interface CategoryService {
    public Boolean saveCategory(Category category);

    public List<Category> getAllCategory();
}
