package com.jogaar.dtos.mappers;

import com.jogaar.dtos.UpdateCreateDto;
import com.jogaar.dtos.UpdateReadDto;
import com.jogaar.entities.Update;

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
public interface UpdateMapper {
    UpdateReadDto toReadDto(Update entity);
    List<UpdateReadDto> toReadDtos(List<Update> entities);
    Update fromCreateDto(UpdateCreateDto createDto);
}
