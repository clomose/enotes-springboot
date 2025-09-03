package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.dto.CategoryResponse;
import com.example.Enotes.entity.Category;
import com.example.Enotes.repository.CategoryRepository;
import com.example.Enotes.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

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

        category.setIsDeleted(false);
        category.setCreatedBy(1);
        Category saveCategory = categoryRepository.save(category);
        if(ObjectUtils.isEmpty(saveCategory)){
            return false;
        }
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categories.stream().map(c -> mapper.map(c,CategoryDto.class)).toList();
        return categoryDtoList;
    }

    @Override
    public List<CategoryResponse> getActiveCategory() {
        List<Category> categories = categoryRepository.findByIsActiveTrue();
        List<CategoryResponse> categoryResponses = categories.stream().map(c -> mapper.map(c,CategoryResponse.class)).toList();
        return  categoryResponses;
    }
}
