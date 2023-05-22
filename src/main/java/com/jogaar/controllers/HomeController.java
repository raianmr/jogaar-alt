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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, Object> stats = new HashMap<>();
        stats.put("successfulCampaigners", userRepo.countSuccessfulCampaigners());
        stats.put("successfulPledgers", userRepo.countSuccessfulPledgers());
        stats.put("totalRaised", userRepo.totalRaised());

        return ResponseEntity.ok(stats);
    }
}
