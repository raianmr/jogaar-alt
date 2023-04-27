package com.jogaar.dtos;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignReadDto {
    @NotNull
    private Long id;
    private LocalDateTime createdAt;

    private String title;
    private String description;
    private String challenges;
    private String faqs;

    @Valid
    private ImageReadDto cover;

    private Long goalAmount;
    private Long pledgedAmount;
    private String currentState;
    private LocalDateTime deadline;
}
