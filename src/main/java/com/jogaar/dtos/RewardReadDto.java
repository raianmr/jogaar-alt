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
public class RewardReadDto {
    @NotNull
    private Long id;
    private LocalDateTime createdAt;

    private String title;
    private String description;
    @Valid
    private ImageReadDto picture;
    private Integer pledgeAmount;

    @Valid
    private CampaignReadDto campaign;
}
