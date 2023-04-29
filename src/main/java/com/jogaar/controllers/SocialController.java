package com.jogaar.controllers;

import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialController {
    UserDao userRepo;
    CampaignDao campaignRepo;

    @Autowired
    public SocialController(UserDao userRepo, CampaignDao campaignRepo) {
        this.userRepo = userRepo;
        this.campaignRepo = campaignRepo;
    }

//    @GetMapping("/")
//    public ResponseEntity<Object> home() {
//        return ResponseEntity.ok("Some useful stats e.g. " +
//                                 "number of successful campaigners, " +
//                                 "number of greenlit campaigns," +
//                                 "number of happy pledgers, etc.");
//    }
}
