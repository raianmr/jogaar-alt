package com.jogaar.dtos.mappers;

import org.mapstruct.MappingTarget;

import java.util.List;

interface ReadMapper<TEntity, TReadDto> {
    TReadDto toReadDto(TEntity entity);

    List<TReadDto> toReadDtos(List<TEntity> entities);
}

interface CreateMapper<TEntity, TCreateDto> {
    TEntity fromCreateDto(TCreateDto createDto);
}

interface UpdateMapper<TEntity, TUpdateDto> {
    void updateEntity(@MappingTarget TEntity entity, TUpdateDto updateDto);
}
