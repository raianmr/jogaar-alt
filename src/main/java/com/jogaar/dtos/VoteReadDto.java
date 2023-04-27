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
public class VoteReadDto {
    @NotNull
    private Long id;
    private LocalDateTime createdAt;

    @Valid
    private UserReadDto user;
    @Valid
    private ReplyReadDto reply;
}
