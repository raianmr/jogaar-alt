package com.jogaar.controller;

import com.jogaar.model.Campaign;
import com.jogaar.model.User;
import com.jogaar.repository.CampaignRepository;
import com.jogaar.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class HomeController {
    UserRepository userRepo;
    CampaignRepository campaignRepo;

    @Autowired
    public HomeController(UserRepository userRepo, CampaignRepository campaignRepo) {
        this.userRepo = userRepo;
        this.campaignRepo = campaignRepo;
    }

    @GetMapping("/")
    public Campaign home() {
        var newU = userRepo.save(User.builder().name("booboo").email(LocalDateTime.now().toString()).password("poopoo").build());

        var newC = campaignRepo.save(Campaign.builder().campaigner(newU).title("booboo").description("choochoo").goalAmount(420L).build());

        return newC;
    }
}
