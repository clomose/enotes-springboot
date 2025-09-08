package com.example.Enotes.ServiceImpl;

import com.example.Enotes.dto.UserDto;
import com.example.Enotes.entity.Role;
import com.example.Enotes.entity.User;
import com.example.Enotes.repository.RoleRepository;
import com.example.Enotes.repository.UserRepository;
import com.example.Enotes.service.UserService;
import com.example.Enotes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Validation validation;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Boolean register(UserDto userDto) throws Exception{
        validation.userValidation(userDto);

        User user = mapper.map(userDto,User.class);
        setRole(userDto,user);
        //very important concept
        //We did it because we need actual role objects from the database
        User save = userRepository.save(user);
        if(ObjectUtils.isEmpty(save)){
            return false;
        }
        return true;
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> roleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepository.findAllById(roleId);
        user.setRoles(roles);
    }
}
