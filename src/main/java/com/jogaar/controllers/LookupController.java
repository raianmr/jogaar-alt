package com.jogaar.controllers;

import com.jogaar.controllers.exceptions.DuplicateForUserException;
import com.jogaar.controllers.exceptions.EmailConflictException;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.ImageDao;
import com.jogaar.daos.UserDao;
import com.jogaar.dtos.ImageReadDto;
import com.jogaar.dtos.UserCreateDto;
import com.jogaar.dtos.UserReadDto;
import com.jogaar.dtos.mappers.ImageMapper;
import com.jogaar.entities.Image;
import com.jogaar.entities.User;
import com.jogaar.security.AuthService;
import com.jogaar.security.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LookupController {
    private final UserDao userRepo;
    private final CampaignDao campaignRepo;
    private final AuthService authService;
    private final AuthHelper authHelper;
    private final ImageDao imageRepo;
    private final ImageMapper imageMapper;
    private final StorageService storageService;

    @PostMapping("/images")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageReadDto uploadImage(@RequestParam("file") MultipartFile file) {
        Image newI;
        try {
            newI = storageService.store(file);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }

        return imageMapper.toReadDto(newI);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id, HttpServletRequest request) {
        Image existingI = storageService.loadImage(id);
        Resource existingR = storageService.loadImageAsResource(existingI);

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(existingI.getFiletype()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + existingR.getFilename() + "\"")
                .body(existingR);
    }
}
