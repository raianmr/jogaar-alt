package com.jogaar.controllers;

import com.jogaar.controllers.exceptions.DuplicateForUserException;
import com.jogaar.controllers.exceptions.NotAllowedException;
import com.jogaar.controllers.exceptions.NotFoundException;
import com.jogaar.controllers.exceptions.ImageNotFoundException;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.ImageDao;
import com.jogaar.daos.UserDao;
import com.jogaar.dtos.CampaignCreateDto;
import com.jogaar.dtos.CampaignReadDto;
import com.jogaar.dtos.CampaignUpdateDto;
import com.jogaar.dtos.mappers.CampaignMapper;
import com.jogaar.dtos.mappers.UserMapper;
import com.jogaar.entities.Campaign;
import com.jogaar.entities.User;
import com.jogaar.security.AuthService;

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

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FundraisingController {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final ImageDao imageDao;
    private final CampaignDao campaignDao;
    private final CampaignMapper campaignMapper;
    private final AuthService authService;
    private final AuthHelper authHelper;


    @PostMapping("/campaigns/{id}/start")
    public CampaignReadDto startCampaign(@PathVariable Long id) {
        var existingC = campaignDao.findById(id).orElseThrow(NotFoundException::new);

        authHelper.currentAuthorOrElseThrow(existingC);

        existingC.setCurrentState(Campaign.State.STARTED);
        existingC = campaignDao.save(existingC);

        return campaignMapper.toReadDto(existingC);
    }

    @GetMapping("/campaigns")
    public List<CampaignReadDto> readCampaigns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @Valid @RequestParam(required = false) Campaign.State state) {
        var result = state != null
                ? campaignDao.findAllByCurrentState(state, PageRequest.of(page, size))
                : campaignDao.findAll(PageRequest.of(page, size));

        return result
                .getContent()
                .stream()
                .map(campaignMapper::toReadDto)
                .toList();
    }

    @GetMapping("/users/{id}/campaigns")
    public List<CampaignReadDto> readCampaignsByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long userId
    ) {
        return campaignDao.findAllByCampaigner(
                        userId != null
                                ? userDao.findById(userId).orElseThrow(NotFoundException::new)
                                : authHelper.currentUserOrElseThrow(),
                        PageRequest.of(page, size)
                )
                .map(campaignMapper::toReadDto)
                .toList();
    }

    @GetMapping("/campaigns/bookmarked")
    public List<CampaignReadDto> readBookmarkedCampaigns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long userId
    ) {
        return campaignDao.findAllBookmarkedByUser(
                    userId != null
                            ? userId
                            : authHelper.currentUserOrElseThrow().getId(),
                    PageRequest.of(page, size)
                )
                .map(campaignMapper::toReadDto)
                .toList();
    }

    @GetMapping("/campaigns/pledged")
    public List<CampaignReadDto> readPledgedCampaigns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long userId
    ) {
        return campaignDao.findAllPledgedByUser(
                        userId != null
                                ? userId
                                : authHelper.currentUserOrElseThrow().getId(),
                        PageRequest.of(page, size)
                )
                .map(campaignMapper::toReadDto)
                .toList();
    }

    @GetMapping("/campaigns/{id}")
    public CampaignReadDto readCampaign(@PathVariable Long id) {
        return campaignDao.findById(id)
                .map(campaignMapper::toReadDto)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/campaigns")
    @ResponseStatus(HttpStatus.CREATED)
    public CampaignReadDto createCampaign(@Valid @RequestBody CampaignCreateDto createDto) {
        try {
            var toBeSaved = campaignMapper.fromCreateDto(createDto);
            toBeSaved.setCampaigner(authHelper.currentUserOrElseThrow());
            var newC = campaignDao.save(toBeSaved);

            return campaignMapper.toReadDto(newC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }
    }

    @PutMapping("/campaigns/{id}")
    public CampaignReadDto updateCampaign(@PathVariable Long id, @Valid @RequestBody CampaignUpdateDto updateDto) {
        var existingC = campaignDao.findById(id).orElseThrow(NotFoundException::new);

        authHelper.currentAuthorOrElseThrow(existingC);

        campaignMapper.updateEntity(existingC, updateDto);
        if (updateDto.getCover() != null) {
            var existingImage = imageDao.findById(updateDto.getCover().getId())
                    .orElseThrow(ImageNotFoundException::new);
            existingC.setCover(existingImage);
        }

        try {
            existingC = campaignDao.save(existingC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }

        return campaignMapper.toReadDto(existingC);
    }

    @DeleteMapping("/campaigns/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCampaign(@PathVariable Long id) {
        var existingC = campaignDao.findById(id).orElseThrow(NotFoundException::new);
        authHelper.currentAuthorOrElseThrow(existingC);

        campaignDao.deleteById(id);
    }
}
