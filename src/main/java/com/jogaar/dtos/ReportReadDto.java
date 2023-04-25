package com.jogaar.dtos;

import com.jogaar.entities.Image;
import com.jogaar.entities.Report;
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
public class ReportReadDto {
    private Long id;
    private LocalDateTime createdAt;

    private String description;
    private Long contentId;
    private Report.Reportable contentType;

    private User reporter;

}
