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
public class CampaignCreateDto {
    private String title;
    private String description;
    private String challenges;
    private String faqs;

    private Long goalAmount;
    private LocalDateTime deadline;
}
