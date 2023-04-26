package com.jogaar.dtos;

import com.jogaar.entities.Image;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String name;
    private String email;
    private String password;

    private String about;
    private String contact;
    private String address;

    @Valid
    private ImageReadDto portrait;
}
