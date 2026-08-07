package com.fitness.userService.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String id;
    private String email;
    private String firstName;
    private String password;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
