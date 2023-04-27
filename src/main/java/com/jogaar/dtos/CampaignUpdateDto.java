package com.jogaar.dtos;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignUpdateDto {
    private String title;
    private String description;
    private String challenges;
    private String faqs;

    private String currentState;

    @Valid
    private ImageReadDto cover;
}
