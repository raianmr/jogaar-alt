package com.jogaar.dtos.mappers;

import com.jogaar.dtos.PledgeCreateDto;
import com.jogaar.dtos.PledgeReadDto;
import com.jogaar.dtos.PledgeUpdateDto;
import com.jogaar.entities.Pledge;

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
public interface PledgeMapper {
    PledgeReadDto toReadDto(Pledge entity);
    List<PledgeReadDto> toReadDtos(List<Pledge> entities);
    Pledge fromCreateDto(PledgeCreateDto createDto);
    void updateEntity(@MappingTarget Pledge entity, PledgeUpdateDto updateDto);
}
