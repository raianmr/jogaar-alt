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
public class MilestoneReadDto {
    private Long id;
    private LocalDateTime createdAt;

    private String title;
    private String description;
    private Image picture;
    private LocalDateTime deadline;
}
