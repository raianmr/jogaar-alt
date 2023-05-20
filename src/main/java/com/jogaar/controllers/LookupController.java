package com.jogaar.controllers;

import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.UserDao;
import com.jogaar.security.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LookupController {
    private final UserDao userRepo;
    private final CampaignDao campaignRepo;
    private final AuthService authService;



//    @GetMapping("/alerts")
//    public ResponseEntity<Object> home() {
//        return ResponseEntity.ok("Some useful stats e.g. " +
//                                 "number of successful campaigners, " +
//                                 "number of greenlit campaigns," +
//                                 "number of happy pledgers, etc.");
//    }
}
