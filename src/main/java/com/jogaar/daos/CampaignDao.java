package com.jogaar.daos;

import com.jogaar.entities.Campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignDao extends JpaRepository<Campaign, Long> {
}
