package com.jogaar.dtos.mappers;

import com.jogaar.dtos.CampaignCreateDto;
import com.jogaar.dtos.CampaignReadDto;
import com.jogaar.dtos.CampaignUpdateDto;
import com.jogaar.entities.Campaign;

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
public interface CampaignMapper {
    CampaignReadDto toReadDto(Campaign entity);
    List<CampaignReadDto> toReadDtos(List<Campaign> entities);
    Campaign fromCreateDto(CampaignCreateDto createDto);
    void updateEntity(@MappingTarget Campaign entity, CampaignUpdateDto updateDto);
}
