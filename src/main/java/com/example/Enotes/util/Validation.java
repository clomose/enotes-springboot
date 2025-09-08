package com.example.Enotes.util;

import com.example.Enotes.dto.CategoryDto;
import com.example.Enotes.dto.TodoDto;
import com.example.Enotes.dto.UserDto;
import com.example.Enotes.entity.Role;
import com.example.Enotes.enums.TodoStatus;
import com.example.Enotes.exception.ResourceAlreadyExists;
import com.example.Enotes.exception.ResourceNotFoundException;
import com.example.Enotes.exception.ValidationException;
import com.example.Enotes.repository.RoleRepository;
import com.example.Enotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Validation {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    public void categoryValidation(CategoryDto  categoryDto){
        Map<String,Object> error = new HashMap<>();

        if(ObjectUtils.isEmpty(categoryDto)){
            throw new IllegalArgumentException("Category Object/JSON shouldn't be null or empty");
        }else{

            //validate name
            if(ObjectUtils.isEmpty(categoryDto.getName())){
                error.put("name","Name field is empty or null");
            }else{
                if (categoryDto.getName().length()<3){
                    error.put("name","Name Length min 3");
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

    public void todoValidation(TodoDto todoDto) throws Exception{
        TodoDto.StatusDto reqStatus = todoDto.getStatus();
        Boolean statusFound = false;
        for (TodoStatus st : TodoStatus.values()) {
            if(st.getId().equals(reqStatus.getId())){
                statusFound=true;
            }
        }
        if(!statusFound){
            throw new ResourceNotFoundException("invalid status");
        }
    }

    public void userValidation(UserDto userDto) throws Exception{
        if (!StringUtils.hasText(userDto.getFirstName())){
            throw new IllegalArgumentException("First name is invalid");
        }
        if (!StringUtils.hasText(userDto.getLastName())){
            throw new IllegalArgumentException("Last name is invalid");
        }
        if (!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constants.EMAIL_REGEX)){
            throw new IllegalArgumentException("email is invalid");
        }else{
            Boolean existEmail = userRepository.existsByEmail(userDto.getEmail());
            if(existEmail){
                throw new ResourceAlreadyExists("Mail Id already exist");
            }
        }
        if (!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constants.MOBNO_REGEX)){
            throw new IllegalArgumentException("mobile number is invalid");
        }

        if(CollectionUtils.isEmpty(userDto.getRoles())){
            throw new IllegalArgumentException("Role is invalid");
        }

        List<Integer> roleIds = roleRepository.findAll().stream().map(r -> r.getId()).toList();

        List<Integer> reqRoleids = userDto.getRoles().stream()
                .map(r -> r.getId())
                .filter(roleId -> !roleIds.contains(roleId)).toList();

        if(!CollectionUtils.isEmpty(reqRoleids)){
            throw new IllegalArgumentException("Role is invalid ");
        }
    }
}
