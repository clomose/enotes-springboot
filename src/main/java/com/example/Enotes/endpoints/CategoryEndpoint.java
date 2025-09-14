package com.example.Enotes.endpoints;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @PostMapping("/save")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @GetMapping("/")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();

    @GetMapping("/active")
    @PreAuthorize(Constants.ANY_ROLE)
    public ResponseEntity<?> getActiveCategory();

    @GetMapping("/{id}")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;

    @DeleteMapping("/{id}")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);
}
