package com.anjaniy.ecommerce_order_service.controller;

import com.anjaniy.ecommerce_order_service.model.dto.ApiResponse;
import com.anjaniy.ecommerce_order_service.model.dto.UserDto;
import com.anjaniy.ecommerce_order_service.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUsers() {
        return ResponseEntity.ok(ApiResponse.<List<UserDto>>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("Users fetched successfully!")
                .data(userService.getUsers())
                .build()
        );
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("id") long id) {
        return ResponseEntity.ok(ApiResponse.<UserDto>builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .message("User fetched successfully!")
                .data(userService.getUser(id))
                .build()
        );
    }
}
