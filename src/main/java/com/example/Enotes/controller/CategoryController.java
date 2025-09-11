package com.example.Enotes.controller;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.dto.CategoryResponse;
import com.example.Enotes.entity.Category;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.service.CategoryService;
import com.example.Enotes.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
        Boolean saveCategory = categoryService.saveCategory(categoryDto);
        if(saveCategory){
            return CommonUtil.createBuildResponseMessage("saved success",HttpStatus.CREATED);
//            return new ResponseEntity<>("saved successfully", HttpStatus.CREATED);
        }
        return CommonUtil.createErrorResponseMessage("not saved",HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryDto> categoriesDto = categoryService.getAllCategory();
        if(CollectionUtils.isEmpty(categoriesDto)){
            return ResponseEntity.noContent().build();
        }
        return  CommonUtil.createBuildResponse(categoriesDto,HttpStatus.OK);
//        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<?> getActiveCategory(){
        List<CategoryResponse> categoriesDto = categoryService.getActiveCategory();
        if(CollectionUtils.isEmpty(categoriesDto)){
            return ResponseEntity.noContent().build();
        }
        return  CommonUtil.createBuildResponse(categoriesDto,HttpStatus.OK);
//        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
        //Without try catch Global Error Handler will execute
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if(ObjectUtils.isEmpty(categoryDto)){
            return CommonUtil.createErrorResponseMessage("Internal Server Error",HttpStatus.NOT_FOUND);
//            return new ResponseEntity<>("Internal Server Error"+id,HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(categoryDto,HttpStatus.OK);
//        return new ResponseEntity<>(categoryDto,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
        Boolean deleted = categoryService.deleteCategoryById(id);
        if(deleted){
            return CommonUtil.createBuildResponseMessage("Category Deleted Successfully",HttpStatus.OK);
//            return new ResponseEntity<>("Category Deleted Successfully",HttpStatus.OK);
        }
        return CommonUtil.createErrorResponseMessage("Category Not Deleted",HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>("Category not deleted",HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
