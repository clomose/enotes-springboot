package com.example.Enotes.endpoints;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category",description = "All the Category operations APIs")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {

    @Operation(summary = "Save Category endpoint",description = "Admin save category")
    @PostMapping("/save")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);

    @Operation(summary = "Get All category endpoint",description = "Admin get all category")
    @GetMapping("/")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getAllCategory();

    @Operation(summary = "Get Active Category endpoint",description = "Admin and User get active category")
    @GetMapping("/active")
    @PreAuthorize(Constants.ANY_ROLE)
    public ResponseEntity<?> getActiveCategory();

    @Operation(summary = "Get Category By Id endpoint",description = "Admin get category by id")
    @GetMapping("/{id}")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Delete Category by Id endpoint",description = "Admin delete category by id")
    @DeleteMapping("/{id}")
    @PreAuthorize(Constants.ROLE_ADMIN)
    public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);
}
