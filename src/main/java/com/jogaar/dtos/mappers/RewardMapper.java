package com.jogaar.dtos.mappers;

import com.jogaar.dtos.RewardCreateDto;
import com.jogaar.dtos.RewardReadDto;
import com.jogaar.dtos.RewardUpdateDto;
import com.jogaar.entities.Reward;

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
public interface RewardMapper {
    RewardReadDto toReadDto(Reward entity);
    List<RewardReadDto> toReadDtos(List<Reward> entities);
    Reward fromCreateDto(RewardCreateDto createDto);
    void updateEntity(@MappingTarget Reward entity, RewardUpdateDto updateDto);
}
