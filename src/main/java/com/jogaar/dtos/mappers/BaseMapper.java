package com.jogaar.dtos.mappers;

import org.mapstruct.MappingTarget;

import java.util.List;

interface ReadMapperBase<TEntity, TReadDto> {
    TReadDto toReadDto(TEntity entity);

    List<TReadDto> toReadDtos(List<TEntity> entities);
}

interface CreateMapperBase<TEntity, TCreateDto> {
    TEntity fromCreateDto(TCreateDto createDto);
}

interface UpdateMapperBase<TEntity, TUpdateDto> {
    void updateEntity(@MappingTarget TEntity entity, TUpdateDto updateDto);
}
