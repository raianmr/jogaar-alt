package com.jogaar.dtos.mappers;

import com.jogaar.dtos.BookmarkReadDto;
import com.jogaar.entities.Bookmark;

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
public interface BookmarkMapper {
    BookmarkReadDto toReadDto(Bookmark entity);
    List<BookmarkReadDto> toReadDtos(List<Bookmark> entities);
}
