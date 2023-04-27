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
public class ReplyReadDto {
    @NotNull
    private Long id;
    private LocalDateTime createdAt;

    private String content;

    @Valid
    private UserReadDto user;
    @Valid
    private UpdateReadDto update;

    private boolean edited;
}
