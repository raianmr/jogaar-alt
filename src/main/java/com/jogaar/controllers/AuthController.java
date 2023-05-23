package com.jogaar.controllers;

import com.jogaar.controllers.exceptions.DuplicateForUserException;
import com.jogaar.controllers.exceptions.EmailConflictException;
import com.jogaar.controllers.exceptions.ImageNotFoundException;
import com.jogaar.controllers.exceptions.NotAllowedException;
import com.jogaar.controllers.exceptions.NotFoundException;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.ImageDao;
import com.jogaar.daos.ReportDao;
import com.jogaar.daos.UserDao;
import com.jogaar.dtos.CampaignReadDto;
import com.jogaar.dtos.ReportCreateDto;
import com.jogaar.dtos.ReportReadDto;
import com.jogaar.dtos.UserCreateDto;
import com.jogaar.dtos.UserLoginDto;
import com.jogaar.dtos.UserLoginResponseDto;
import com.jogaar.dtos.UserReadDto;
import com.jogaar.dtos.UserUpdateDto;
import com.jogaar.dtos.mappers.ReportMapper;
import com.jogaar.dtos.mappers.UserMapper;
import com.jogaar.entities.Campaign;
import com.jogaar.entities.Image;
import com.jogaar.entities.Reply;
import com.jogaar.entities.Report;
import com.jogaar.entities.User;
import com.jogaar.security.AuthService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final ReportDao reportDao;
    private final ImageDao imageDao;
    private final AuthService authService;
    private final AuthHelper authHelper;
    private final ReportMapper reportMapper;

    @GetMapping("/users")
    public List<UserReadDto> readUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid @RequestParam(required = false) User.Access access) {
        var result = access != null
                ? userDao.findAllByAccessLevel(access, PageRequest.of(page, size))
                : userDao.findAll(PageRequest.of(page, size));

        return result
                .getContent()
                .stream()
                .map(userMapper::toReadDto)
                .toList();
    }

    @GetMapping("/users/supers")
    public List<UserReadDto> readSuperUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userDao.findAllSuperUsers(PageRequest.of(page, size))
                .getContent()
                .stream()
                .map(userMapper::toReadDto)
                .toList();
    }

    @GetMapping("/users/current")
    public UserReadDto readCurrentUser() {
        return authService
                .getCurrentUser()
                .map(userMapper::toReadDto)
                .orElseThrow(NotFoundException::new);
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
        User newU;
        try {
            newU = authService.register(createDto);
        } catch (DataIntegrityViolationException exception) {
            throw new EmailConflictException();
        }

        return userMapper.toReadDto(newU);
    }

    @PostMapping("/login")
    public UserLoginResponseDto loginUser(@Valid @RequestBody UserLoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PutMapping("/users/{id}")
    public UserReadDto updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto updateDto) {
        var existingU = userDao.findById(id).orElseThrow(NotFoundException::new);

        authHelper.currentAuthorOrElseThrow(existingU);

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
        var existingU = userDao.findById(id).orElseThrow(NotFoundException::new);
        authHelper.currentAuthorOrElseThrow(existingU);

        userDao.deleteById(id);
    }

    @PostMapping("/users/{id}/ban")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserReadDto banUser(@PathVariable Long id) {
        authHelper.currentSuperOrElseThrow();

        var existingU = userDao.findById(id).orElseThrow(NotFoundException::new);

        existingU.setAccessLevel(User.Access.BANNED);
        existingU = userDao.save(existingU);

        return userMapper.toReadDto(existingU);
    }

    @PostMapping("/users/{id}/promote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserReadDto promoteUser(@PathVariable Long id) {
        authHelper.currentSuperOrElseThrow();

        var existingU = userDao.findById(id).orElseThrow(NotFoundException::new);

        existingU.setAccessLevel(User.Access.MOD);
        existingU = userDao.save(existingU);

        return userMapper.toReadDto(existingU);
    }

    @PostMapping("/users/{id}/demote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UserReadDto demoteUser(@PathVariable Long id) {
        authHelper.currentSuperOrElseThrow();

        var existingU = userDao.findById(id).orElseThrow(NotFoundException::new);

        existingU.setAccessLevel(User.Access.NORMAL);
        existingU = userDao.save(existingU);

        return userMapper.toReadDto(existingU);
    }

    @GetMapping("/reports")
    public List<ReportReadDto> readReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid @RequestParam(required = false) Report.Reportable type) {
        var result = type != null
                ? reportDao.findAllByContentTypeOrderByCreatedAtDesc(type, PageRequest.of(page, size))
                : reportDao.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        return result
                .getContent()
                .stream()
                .map(reportMapper::toReadDto)
                .toList();
    }

    @PostMapping("/reports")
    public ReportReadDto createReport(@Valid @RequestBody ReportCreateDto createDto) {
        User currentU = authHelper.currentUserOrElseThrow();

        try {
            var toBeSaved = reportMapper.fromCreateDto(createDto);
            toBeSaved.setReporter(currentU);
            var newC = reportDao.save(toBeSaved);

            return reportMapper.toReadDto(newC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }
    }

    @DeleteMapping("/reports/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReport(@PathVariable Long id) {
        var existingR = reportDao.findById(id).orElseThrow(NotFoundException::new);

        authHelper.currentSuperOrElseThrow();

        reportDao.deleteById(existingR.getId());
    }
}
