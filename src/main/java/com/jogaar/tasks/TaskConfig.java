package com.jogaar.tasks;

import com.jogaar.daos.CampaignDao;
import com.jogaar.daos.UserDao;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TaskConfig {
    private final UserDao userDao;
    private final CampaignDao campaignDao;

    @Scheduled(fixedRate = 30000)
    public void updateCampaigns() {
        campaignDao.updateAllEndedCampaigns();
    }
}
