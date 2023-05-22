package com.jogaar.security;

import com.jogaar.controllers.AuthHelper;
import com.jogaar.daos.ImageDao;
import com.jogaar.entities.Image;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final AuthHelper authHelper;
    private final ConfigProps configProps;
    private final ImageDao imageDao;

    public Image store(MultipartFile file) {
        Path path = Paths
                .get(configProps.staticDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(path);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not create directory");
        }

        long count = imageDao.count();
        String filename = StringUtils.cleanPath(
                file.getOriginalFilename() == null
                        ? "-"
                        : file.getOriginalFilename()
        );
        Path target = path.resolve(Long.toString(count) + "-" + filename);

        try {
            Files.copy(
                    file.getInputStream(),
                    target,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "could not store image");
        }


        Image newI = Image
                .builder()
                .uploader(authHelper.currentUserOrElseThrow())
                .filename(filename)
                .filetype(file.getContentType())
                .location(target.toString())
                .build();
        newI = imageDao.save(newI);

        return newI;
    }

    public Image loadImage(Long imageId) {
        return imageDao.findById(imageId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "requested image was not found"));
    }

    public Resource loadImageAsResource(Image existingI) {
        Path path = Paths.get(existingI.getLocation()).toAbsolutePath().normalize();

        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "requested image was not found");
        }

        if (resource.exists()) {
            return resource;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "requested image was not found");
        }
    }
}
