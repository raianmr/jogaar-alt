package com.jogaar.dtos;

import com.jogaar.entities.Image;
import com.jogaar.entities.Update;
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
public class ReplyReadDto {
    private Long id;
    private LocalDateTime createdAt;

    private String content;

    private User user;
    private Update update;

    private boolean edited;
}
