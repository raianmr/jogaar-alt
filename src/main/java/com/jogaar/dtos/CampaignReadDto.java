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
public class CampaignReadDto {
    private Long id;
    private LocalDateTime createdAt;

    private String title;
    private String description;
    private String challenges;
    private String faqs;

    private Image cover;

    private Long goalAmount;
    private Long pledgedAmount;
    private Campaign.State currentState;
    private LocalDateTime deadline;
}
