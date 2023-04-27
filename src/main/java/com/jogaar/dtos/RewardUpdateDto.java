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
public class RewardUpdateDto {
    private String title;
    private String description;
    @Valid
    private ImageReadDto picture;
    private Integer pledgeAmount;
}
