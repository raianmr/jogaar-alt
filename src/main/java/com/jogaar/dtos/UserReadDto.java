package com.jogaar.dtos;

import com.jogaar.entities.Image;
import com.jogaar.entities.User;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDto {
    private Long id;
    private LocalDateTime createdAt;

    private String name;
    private String email;
    private String password;

    private String about;
    private String contact;
    private String address;

    private Image portrait;
    private User.Access accessLevel;
}
