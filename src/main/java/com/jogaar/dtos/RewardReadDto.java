package com.jogaar.dtos;

import com.jogaar.entities.Campaign;
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
public class RewardReadDto {
    private Long id;
    private LocalDateTime createdAt;

    private String title;
    private String description;
    private Image picture;
    private Integer pledgeAmount;

    private Campaign campaign;
}
