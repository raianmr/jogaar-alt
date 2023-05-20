package com.jogaar.controllers;

import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SocialController {
    private final UserDao userRepo;
    private final CampaignDao campaignRepo;


//    @GetMapping("/")
//    public ResponseEntity<Object> home() {
//        return ResponseEntity.ok("Some useful stats e.g. " +
//                                 "number of successful campaigners, " +
//                                 "number of greenlit campaigns," +
//                                 "number of happy pledgers, etc.");
//    }
}
