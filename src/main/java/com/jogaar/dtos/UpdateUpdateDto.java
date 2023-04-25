package com.jogaar.dtos;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.Image;
import com.jogaar.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUpdateDto {
    private String title;
    private String content;
    private Image picture;
}
