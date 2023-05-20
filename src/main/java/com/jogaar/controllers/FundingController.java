package com.jogaar.controllers;

import com.jogaar.security.AuthService;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FundingController {
    private final AuthService authService;

}
