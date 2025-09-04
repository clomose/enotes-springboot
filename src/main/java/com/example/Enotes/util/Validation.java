package com.example.Enotes.util;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class Validation {

    public void categoryValidation(CategoryDto  categoryDto){
        Map<String,Object> error = new HashMap<>();

        if(ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("Category Object/JSON shouldn't be null or empty");
        }else{

            //validate name
            if(ObjectUtils.isEmpty(categoryDto.getName())){
                error.put("name","Name field is empty or null");
            }else{
                if (categoryDto.getName().length()<10){
                    error.put("name","Name Length min 10");
                }
                if (categoryDto.getName().length()>100){
                    error.put("name","name length max 100");
                }
            }

            //validate description
            if(ObjectUtils.isEmpty(categoryDto.getDescription())){
                error.put("description","description field is empty or null");
            }else{
                if (categoryDto.getDescription().length()<10){
                    error.put("description","description Length min 10");
                }
                if (categoryDto.getDescription().length()>100){
                    error.put("description","description length max 100");
                }
            }

            //validate isActive
            if(ObjectUtils.isEmpty(categoryDto.getIsActive())){
                error.put("isActive","isActive field is empty or null");
            }else {
                if(categoryDto.getIsActive()!=true && !categoryDto.getIsActive()!=false){
                    error.put("isActive","Invalid value in isActive filed ");
                }
            }
        }
        if (!error.isEmpty()){
            throw new ValidationException(error);
        }
    }
}
