package com.jogaar.controllers;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.User;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class HomeController {
    UserDao userRepo;
    CampaignDao campaignRepo;

    @Autowired
    public HomeController(UserDao userRepo, CampaignDao campaignRepo) {
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
