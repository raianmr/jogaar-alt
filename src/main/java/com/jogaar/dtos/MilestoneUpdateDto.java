package com.jogaar.dtos;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneUpdateDto {
    private String title;
    private String description;
    @Valid
    private ImageReadDto picture;
    private LocalDateTime deadline;
}
