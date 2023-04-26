package com.jogaar.dtos.mappers;

import com.jogaar.dtos.UserCreateDto;
import com.jogaar.dtos.UserReadDto;
import com.jogaar.dtos.UserUpdateDto;
import com.jogaar.entities.User;

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
public interface UserMapper {
    UserReadDto toReadDto(User entity);
    List<UserReadDto> toReadDtos(List<User> entities);
    User fromCreateDto(UserCreateDto createDto);
    void updateEntity(@MappingTarget User entity, UserUpdateDto updateDto);
}
//public interface UserMapper extends
//        ReadMapper<User, UserReadDto>,
//        CreateMapper<User, UserCreateDto>,
//        UpdateMapper<User, UserUpdateDto> { }