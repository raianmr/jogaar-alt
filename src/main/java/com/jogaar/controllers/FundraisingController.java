package com.jogaar.controllers;

import com.jogaar.controllers.exceptions.DuplicateForUserException;
import com.jogaar.controllers.exceptions.NotAllowedException;
import com.jogaar.controllers.exceptions.NotFoundException;
import com.jogaar.controllers.exceptions.ImageNotFoundException;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.ImageDao;
import com.jogaar.daos.MilestoneDao;
import com.jogaar.daos.RewardDao;
import com.jogaar.daos.TagDao;
import com.jogaar.daos.UpdateDao;
import com.jogaar.daos.UserDao;
import com.jogaar.dtos.CampaignCreateDto;
import com.jogaar.dtos.CampaignReadDto;
import com.jogaar.dtos.CampaignUpdateDto;
import com.jogaar.dtos.MilestoneCreateDto;
import com.jogaar.dtos.MilestoneReadDto;
import com.jogaar.dtos.RewardCreateDto;
import com.jogaar.dtos.RewardReadDto;
import com.jogaar.dtos.TagCreateDto;
import com.jogaar.dtos.TagReadDto;
import com.jogaar.dtos.UpdateCreateDto;
import com.jogaar.dtos.UpdateReadDto;
import com.jogaar.dtos.mappers.CampaignMapper;
import com.jogaar.dtos.mappers.ImageMapper;
import com.jogaar.dtos.mappers.MilestoneMapper;
import com.jogaar.dtos.mappers.RewardMapper;
import com.jogaar.dtos.mappers.TagMapper;
import com.jogaar.dtos.mappers.UpdateMapper;
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
    private final ImageMapper imageMapper;

    private final CampaignDao campaignDao;
    private final CampaignMapper campaignMapper;

    private final MilestoneDao milestoneDao;
    private final MilestoneMapper milestoneMapper;

    private final RewardDao rewardDao;
    private final RewardMapper rewardMapper;

    private final TagDao tagDao;
    private final TagMapper tagMapper;

    private final UpdateDao updateDao;
    private final UpdateMapper updateMapper;

    private final AuthService authService;
    private final AuthHelper authHelper;

    @PostMapping("/campaigns/{id}/lock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CampaignReadDto lockCampaign(@PathVariable Long id) {
        var existingC = campaignDao.findById(id).orElseThrow(NotFoundException::new);

        authHelper.currentSuperOrElseThrow();

        existingC.setCurrentState(Campaign.State.LOCKED);
        existingC = campaignDao.save(existingC);

        return campaignMapper.toReadDto(existingC);
    }

    @PostMapping("/campaigns/{id}/greenlight")
    public CampaignReadDto greenlightCampaign(@PathVariable Long id) {
        var existingC = campaignDao.findById(id).orElseThrow(NotFoundException::new);

        authHelper.currentSuperOrElseThrow();

        existingC.setCurrentState(Campaign.State.GREENLIT);
        existingC = campaignDao.save(existingC);

        return campaignMapper.toReadDto(existingC);
    }

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

    @GetMapping("/users/{userId}/campaigns")
    public List<CampaignReadDto> readCampaignsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var existingU = userDao.findById(userId).orElseThrow(NotFoundException::new);

        return campaignDao.findAllByCampaigner(
                        existingU,
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

    // milestones

    @GetMapping("/campaigns/{campaignId}/milestones")
    public List<MilestoneReadDto> readMilestones(
            @PathVariable Long campaignId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        return milestoneDao.findAllByCampaign(existingC, PageRequest.of(page, size))
                .map(milestoneMapper::toReadDto)
                .toList();
    }

    @PostMapping("/campaigns/{campaignId}/milestones")
    @ResponseStatus(HttpStatus.CREATED)
    public MilestoneReadDto createCampaign(
            @PathVariable Long campaignId,
            @Valid @RequestBody MilestoneCreateDto createDto
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        try {
            var toBeSaved = milestoneMapper.fromCreateDto(createDto);
            toBeSaved.setCampaign(existingC);
            var newC = milestoneDao.save(toBeSaved);

            return milestoneMapper.toReadDto(newC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }
    }

    // rewards
    @GetMapping("/campaigns/{campaignId}/rewards")
    public List<RewardReadDto> readRewards(
            @PathVariable Long campaignId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        return rewardDao.findAllByCampaign(existingC, PageRequest.of(page, size))
                .map(rewardMapper::toReadDto)
                .toList();
    }

    @PostMapping("/campaigns/{campaignId}/rewards")
    @ResponseStatus(HttpStatus.CREATED)
    public RewardReadDto createCampaign(
            @PathVariable Long campaignId,
            @Valid @RequestBody RewardCreateDto createDto
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        try {
            var toBeSaved = rewardMapper.fromCreateDto(createDto);
            toBeSaved.setCampaign(existingC);
            var newC = rewardDao.save(toBeSaved);

            return rewardMapper.toReadDto(newC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }
    }

    // tags

    @GetMapping("/campaigns/{campaignId}/tags")
    public List<TagReadDto> readTags(
            @PathVariable Long campaignId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        return tagDao.findAllByCampaign(existingC, PageRequest.of(page, size))
                .map(tagMapper::toReadDto)
                .toList();
    }

    @PostMapping("/campaigns/{campaignId}/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public TagReadDto createTag(
            @PathVariable Long campaignId,
            @Valid @RequestBody TagCreateDto createDto
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        try {
            var toBeSaved = tagMapper.fromCreateDto(createDto);
            toBeSaved.setCampaign(existingC);
            var newC = tagDao.save(toBeSaved);

            return tagMapper.toReadDto(newC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }
    }

    // updates

    @GetMapping("/campaigns/{campaignId}/updates")
    public List<UpdateReadDto> readUpdates(
            @PathVariable Long campaignId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        return updateDao.findAllByCampaign(existingC, PageRequest.of(page, size))
                .map(updateMapper::toReadDto)
                .toList();
    }

    @PostMapping("/campaigns/{campaignId}/updates")
    @ResponseStatus(HttpStatus.CREATED)
    public UpdateReadDto createUpdate(
            @PathVariable Long campaignId,
            @Valid @RequestBody UpdateCreateDto createDto
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);
        User currentU = authHelper.currentAuthorOrElseThrow(existingC);

        try {
            var toBeSaved = updateMapper.fromCreateDto(createDto);
            toBeSaved.setCampaign(existingC);
            toBeSaved.setUser(currentU);
            var newC = updateDao.save(toBeSaved);

            return updateMapper.toReadDto(newC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }
    }
}
