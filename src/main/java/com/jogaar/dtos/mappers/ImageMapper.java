package com.jogaar.dtos.mappers;

import com.jogaar.dtos.ImageReadDto;
import com.jogaar.entities.Image;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ImageMapper {
    ImageReadDto toReadDto(Image entity);
    List<ImageReadDto> toReadDtos(List<Image> entities);
}
