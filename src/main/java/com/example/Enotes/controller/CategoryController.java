package com.example.Enotes.controller;

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
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        Boolean saveCategory = categoryService.saveCategory(category);
        if(saveCategory){
            return new ResponseEntity<>("saved successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory(){
        List<Category> categories = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categories)){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

}
