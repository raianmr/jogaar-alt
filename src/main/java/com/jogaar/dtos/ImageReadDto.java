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
public class ImageReadDto {
    @NotNull
    private Long id;
    private LocalDateTime createdAt;

    private String filename;
    private String filetype;
    private String location;
}
