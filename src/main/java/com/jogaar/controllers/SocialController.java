package com.jogaar.controllers;

import com.jogaar.controllers.exceptions.NotAllowedException;
import com.jogaar.controllers.exceptions.NotFoundException;
import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.UserDao;
import com.jogaar.entities.Campaign;
import com.jogaar.entities.Reply;
import com.jogaar.entities.User;
import com.jogaar.security.AuthService;

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
    private final AuthService authService;
    private final AuthHelper authHelper;

}
