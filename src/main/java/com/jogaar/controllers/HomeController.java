package com.jogaar.controllers;

import static java.util.stream.Collectors.toList;

import com.jogaar.dtos.CampaignReadDto;
import com.jogaar.dtos.mappers.CampaignMapper;
import com.jogaar.entities.Campaign;
import com.jogaar.entities.User;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.UserDao;
import com.jogaar.security.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final UserDao userRepo;
    private final CampaignDao campaignRepo;
    private final AuthService authService;
    private final AuthHelper authHelper;
    private final CampaignMapper campaignMapper;

    @GetMapping("/")
    public ResponseEntity<Object> home() {
        return ResponseEntity.ok("Some useful stats e.g. " +
                                 "number of successful campaigners, " +
                                 "number of greenlit campaigns," +
                                 "number of happy pledgers, etc.");
    }

    @GetMapping("/test")
    public List<CampaignReadDto> test() {
        return campaignRepo
                .fuzzySearchCampaigns(
                        "string",
                        PageRequest.of(0, 10))
                .getContent()
                .stream().map(campaignMapper::toReadDto)
                .toList();
    }
}
