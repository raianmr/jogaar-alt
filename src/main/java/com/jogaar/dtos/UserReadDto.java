package com.jogaar.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReadDto {
    @NotNull
    private Long id;
    private LocalDateTime createdAt;

    private String name;
    private String email;

    private String about;
    private String contact;
    private String address;

    private ImageReadDto portrait;
    private String accessLevel;
}
