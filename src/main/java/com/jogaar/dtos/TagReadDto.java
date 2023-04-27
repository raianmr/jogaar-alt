package com.jogaar.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagReadDto {
    @NotNull
    private Long id;
    private LocalDateTime createdAt;

    private String name;

    private CampaignReadDto campaign;
}
