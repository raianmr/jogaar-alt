package com.jogaar.dtos;

import com.jogaar.entities.Image;

import java.time.LocalDateTime;

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
    private Image picture;
    private LocalDateTime deadline;
}
