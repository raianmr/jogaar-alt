package com.jogaar.dtos.mappers;

import com.jogaar.dtos.TagCreateDto;
import com.jogaar.dtos.TagReadDto;
import com.jogaar.dtos.TagUpdateDto;
import com.jogaar.entities.Tag;

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
public interface TagMapper {
    TagReadDto toReadDto(Tag entity);
    List<TagReadDto> toReadDtos(List<Tag> entities);
    Tag fromCreateDto(TagCreateDto createDto);
    void updateEntity(@MappingTarget Tag entity, TagUpdateDto updateDto);
}
