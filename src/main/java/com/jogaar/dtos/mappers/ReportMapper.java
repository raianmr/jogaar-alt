package com.jogaar.dtos.mappers;

import com.jogaar.dtos.ReportCreateDto;
import com.jogaar.dtos.ReportReadDto;
import com.jogaar.entities.Report;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = UserMapper.class
)
public interface ReportMapper {
    ReportReadDto toReadDto(Report entity);
    List<ReportReadDto> toReadDtos(List<Report> entities);
    Report fromCreateDto(ReportCreateDto createDto);
}
