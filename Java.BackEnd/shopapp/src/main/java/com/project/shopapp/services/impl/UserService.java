package com.project.shopapp.services.impl;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        User user = User.builder()
            .fullName(userDTO.getFullName())
            .phoneNumber(userDTO.getPhoneNumber())
            .password(userDTO.getPassword())
            .address(userDTO.getAddress())
            .dateOfBirth(userDTO.getDateOfBirth())
            .facebookAccountId(userDTO.getFacebookAccountId())
            .googleAccountId(userDTO.getGoogleAccountId())
            .active(true)
            .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
            .orElseThrow(() -> new DataNotFoundException("Role not found"));
        user.setRole(role);
        
        if(userDTO.getFacebookAccountId() ==0 && userDTO.getGoogleAccountId() ==0){
            String password = userDTO.getPassword();
//            String encodedPassword = passwordEncoder.encode(password);
//            user.setPassword(encodedPassword);
        }
        return userRepository.save(user);
    }
    
    @Override
    public String login(String phoneNumber, String password) {
        return null;
    }
}
