package com.jogaar.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String challenges;
    private String faqs;

    @NotNull
    private Long goalAmount;
    @NotNull @Future
    private LocalDateTime deadline;
}
