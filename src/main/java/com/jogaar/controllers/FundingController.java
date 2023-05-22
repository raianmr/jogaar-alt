package com.jogaar.controllers;

import com.jogaar.controllers.exceptions.DuplicateForUserException;
import com.jogaar.controllers.exceptions.NotFoundException;
import com.jogaar.daos.BookmarkDao;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.ImageDao;
import com.jogaar.daos.PledgeDao;
import com.jogaar.daos.UserDao;
import com.jogaar.dtos.BookmarkReadDto;
import com.jogaar.dtos.PledgeCreateDto;
import com.jogaar.dtos.PledgeReadDto;
import com.jogaar.dtos.mappers.BookmarkMapper;
import com.jogaar.dtos.mappers.CampaignMapper;
import com.jogaar.dtos.mappers.ImageMapper;
import com.jogaar.dtos.mappers.PledgeMapper;
import com.jogaar.dtos.mappers.UserMapper;
import com.jogaar.entities.Bookmark;
import com.jogaar.entities.Campaign;
import com.jogaar.entities.Pledge;
import com.jogaar.entities.User;
import com.jogaar.security.AuthService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FundingController {
    private final UserDao userDao;
    private final UserMapper userMapper;

    private final ImageDao imageDao;
    private final ImageMapper imageMapper;

    private final CampaignDao campaignDao;
    private final CampaignMapper campaignMapper;

    private final BookmarkDao bookmarkDao;
    private final BookmarkMapper bookmarkMapper;

    private final PledgeDao pledgeDao;
    private final PledgeMapper pledgeMapper;

    private final AuthService authService;
    private final AuthHelper authHelper;

    // pledges

    @GetMapping("/campaigns/{campaignId}/pledges")
    public List<PledgeReadDto> readPledges(
            @PathVariable Long campaignId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        return pledgeDao.findAllByCampaign(existingC, PageRequest.of(page, size))
                .map(pledgeMapper::toReadDto)
                .toList();
    }

    @PostMapping("/campaigns/{campaignId}/pledges")
    @ResponseStatus(HttpStatus.CREATED)
    public PledgeReadDto createPledge(
            @PathVariable Long campaignId,
            @Valid @RequestBody PledgeCreateDto createDto
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);
        User currentU = authHelper.currentUserOrElseThrow();

        try {
            var toBeSaved = pledgeMapper.fromCreateDto(createDto);
            toBeSaved.setCampaign(existingC);
            toBeSaved.setPledger(currentU);
            var newC = pledgeDao.save(toBeSaved);

            return pledgeMapper.toReadDto(newC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }
    }

    // bookmarks

    @GetMapping("/campaigns/{campaignId}/bookmarks")
    public List<BookmarkReadDto> readBookmarks(
            @PathVariable Long campaignId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);

        return bookmarkDao.findAllByCampaign(existingC, PageRequest.of(page, size))
                .map(bookmarkMapper::toReadDto)
                .toList();
    }

    @PostMapping("/campaigns/{campaignId}/bookmarks")
    @ResponseStatus(HttpStatus.CREATED)
    public BookmarkReadDto createBookmark(
            @PathVariable Long campaignId
    ) {
        Campaign existingC = campaignDao.findById(campaignId).orElseThrow(NotFoundException::new);
        User currentU = authHelper.currentUserOrElseThrow();

        try {
            var toBeSaved = new Bookmark();
            toBeSaved.setCampaign(existingC);
            toBeSaved.setUser(currentU);
            var newC = bookmarkDao.save(toBeSaved);

            return bookmarkMapper.toReadDto(newC);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateForUserException();
        }
    }
}
