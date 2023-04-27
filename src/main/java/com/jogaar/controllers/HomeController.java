package com.jogaar.controllers;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.User;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class HomeController {
    UserDao userRepo;
    CampaignDao campaignRepo;

    @Autowired
    public HomeController(UserDao userRepo, CampaignDao campaignRepo) {
        this.userRepo = userRepo;
        this.campaignRepo = campaignRepo;
    }

    @GetMapping("/")
    public ResponseEntity<Object> home() {
        return ResponseEntity.ok("Some useful stats e.g. " +
                                 "number of successful campaigners, " +
                                 "number of greenlit campaigns," +
                                 "number of happy pledgers, etc.");
    }
}
