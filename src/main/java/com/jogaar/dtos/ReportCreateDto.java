package com.jogaar.dtos;

import com.jogaar.entities.Report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCreateDto {
    private String description;
    private Long contentId;
    private Report.Reportable contentType;
}
