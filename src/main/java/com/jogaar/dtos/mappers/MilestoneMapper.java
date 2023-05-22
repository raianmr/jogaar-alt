package com.jogaar.dtos.mappers;

import com.jogaar.dtos.MilestoneCreateDto;
import com.jogaar.dtos.MilestoneReadDto;
import com.jogaar.dtos.MilestoneUpdateDto;
import com.jogaar.entities.Milestone;

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
        uses = {UserMapper.class, CampaignMapper.class}
)
public interface MilestoneMapper {
    MilestoneReadDto toReadDto(Milestone entity);
    List<MilestoneReadDto> toReadDtos(List<Milestone> entities);
    Milestone fromCreateDto(MilestoneCreateDto createDto);
    void updateEntity(@MappingTarget Milestone entity, MilestoneUpdateDto updateDto);
}
