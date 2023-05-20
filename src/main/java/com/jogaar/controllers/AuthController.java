package com.jogaar.controllers;

import com.jogaar.controllers.exceptions.EmailConflictException;
import com.jogaar.controllers.exceptions.ImageNotFoundException;
import com.jogaar.controllers.exceptions.NotFoundException;
import com.jogaar.daos.ImageDao;
import com.jogaar.daos.UserDao;
import com.jogaar.dtos.UserCreateDto;
import com.jogaar.dtos.UserLoginDto;
import com.jogaar.dtos.UserLoginResponseDto;
import com.jogaar.dtos.UserReadDto;
import com.jogaar.dtos.UserUpdateDto;
import com.jogaar.dtos.mappers.UserMapper;
import com.jogaar.entities.User;
import com.jogaar.security.AuthService;

import org.hibernate.boot.model.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final ImageDao imageDao;
    private final AuthService authService;

    @GetMapping("/users")
    public List<UserReadDto> readUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userDao
                .findAll(PageRequest.of(page, size))
                .getContent()
                .stream()
                .map(userMapper::toReadDto)
                .toList();
    }

    @GetMapping("/users/{id}")
    public UserReadDto readUser(@PathVariable Long id) {
        return userDao.findById(id)
                .map(userMapper::toReadDto)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto createUser(@Valid @RequestBody UserCreateDto createDto) {
        UserReadDto newU;
        try {
            newU = authService.register(createDto);
        } catch (DataIntegrityViolationException exception) {
            throw new EmailConflictException();
        }

        return newU;
    }

    @PostMapping("/login")
    public UserLoginResponseDto loginUser(@Valid @RequestBody UserLoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/test")
    public String test() {
        return "test";
    }
    
    @PutMapping("/users/{id}")
    public UserReadDto updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto updateDto) {
        // TODO authorization

        var existingU = userDao.findById(id).orElseThrow(NotFoundException::new);
        userMapper.updateEntity(existingU, updateDto);

        if (updateDto.getPassword() != null) {
            authService.updatePassword(existingU, updateDto.getPassword());
        }

        if (updateDto.getPortrait() != null) {
            var existingImage = imageDao.findById(updateDto.getPortrait().getId())
                    .orElseThrow(ImageNotFoundException::new);
            existingU.setPortrait(existingImage);
        }

        try {
            existingU = userDao.save(existingU);
        } catch (DataIntegrityViolationException exception) {
            throw new EmailConflictException();
        }

        return userMapper.toReadDto(existingU);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        // TODO authorization

        userDao.deleteById(id);
    }
}
