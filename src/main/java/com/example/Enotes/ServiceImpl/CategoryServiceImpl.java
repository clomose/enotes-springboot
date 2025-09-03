package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.dto.CategoryResponse;
import com.example.Enotes.entity.Category;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.repository.CategoryRepository;
import com.example.Enotes.service.CategoryService;
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

    @Override
    public Boolean saveCategory(CategoryDto categoryDto) {

//        Category category = new Category();
//        category.setName(categoryDto.getName());
//        category.setDescription(categoryDto.getDescription());
//        category.setIsActive(categoryDto.getIsActive());

        Category category = mapper.map(categoryDto,Category.class);

        if(ObjectUtils.isEmpty(category.getId())) {
            category.setIsDeleted(false);
            category.setCreatedBy(1);
        }else{
            updateCategory(category);
        }

        Category saveCategory = categoryRepository.save(category);

        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    private void updateCategory(Category category) {
        Optional<Category> findById = categoryRepository.findById(category.getId());
        if(findById.isPresent()){
            Category existCategory = findById.get();
            category.setCreatedBy(existCategory.getId());
            category.setIsDeleted(existCategory.getIsDeleted());
            category.setCreatedOn(existCategory.getCreatedOn());
            category.setUpdatedBy(1);
            category.setUpdatedOn(new Date());
        }
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
