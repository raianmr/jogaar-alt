package com.jogaar.dtos;

import com.jogaar.entities.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardUpdateDto {
    private String title;
    private String description;
    private Image picture;
    private Integer pledgeAmount;
}
