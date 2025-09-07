package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.dto.CategoryResponse;
import com.example.Enotes.entity.Category;
import com.example.Enotes.exception.ResourceAlreadyExists;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.repository.CategoryRepository;
import com.example.Enotes.service.CategoryService;
import com.example.Enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {
        //Validation Checking
        validation.categoryValidation(categoryDto);

        //check category exist or not
        if(ObjectUtils.isEmpty(categoryDto.getId())){
            Boolean check = categoryRepository.existsByName(categoryDto.getName().trim());
            if(check){
                //throw exception
                throw new ResourceAlreadyExists("Category Already exists");
            }
        }
        Category category = mapper.map(categoryDto,Category.class);
        category.setIsDeleted(false);
        Category saveCategory = categoryRepository.save(category);

        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findByIsDeletedFalse();
        List<CategoryDto> categoryDtoList = categories.stream().map(c -> mapper.map(c,CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        List<CategoryResponse> categoryResponses = categories.stream().map(c -> mapper.map(c,CategoryResponse.class)).toList();
        return  categoryResponses;
    }

    @Override
    public CategoryDto getCategoryById(Integer id) throws Exception{
        Category findByCategory = categoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id "+id));
        if(ObjectUtils.isEmpty(findByCategory)){
            CategoryDto categoryDto = mapper.map(findByCategory, CategoryDto.class);
            return categoryDto;
        }
        return  null;
    }

    @Override
    public Boolean deleteCategoryById(Integer id) {
        Optional<Category> findByCategory = categoryRepository.findById(id);
        if(findByCategory.isPresent()){
            Category category = findByCategory.get();
            category.setIsDeleted(true);
            categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
