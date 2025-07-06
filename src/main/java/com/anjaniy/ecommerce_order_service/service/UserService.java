package com.anjaniy.ecommerce_order_service.service;

import com.anjaniy.ecommerce_order_service.exception.BadRequestException;
import com.anjaniy.ecommerce_order_service.exception.ResourceNotFoundException;
import com.anjaniy.ecommerce_order_service.model.dto.UserDto;
import com.anjaniy.ecommerce_order_service.model.entity.User;
import com.anjaniy.ecommerce_order_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(user -> UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build()).toList();
    }

    public UserDto getUser(long id) {
        if(id <= 0) {
            throw new BadRequestException("User ID must be a positive number!");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id + "!"));

        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
