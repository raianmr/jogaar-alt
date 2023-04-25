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
public class PledgeReadDto {
    private Long id;
    private LocalDateTime createdAt;

    private Long amount;

    private User pledger;
    private Campaign campaign;
}
