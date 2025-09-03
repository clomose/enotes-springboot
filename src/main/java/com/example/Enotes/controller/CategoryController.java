package com.example.Enotes.controller;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.dto.CategoryResponse;
import com.example.Enotes.entity.Category;
import com.example.Enotes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if(saveCategory){
            return new ResponseEntity<>("saved successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> categoriesDto = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categoriesDto)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

    @GetMapping("/active-category")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> categoriesDto = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(categoriesDto)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

}
