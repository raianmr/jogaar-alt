package com.jogaar.daos;

import com.jogaar.entities.Campaign;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignDao extends JpaRepository<Campaign, Long> {
    Page<Campaign> findAllByCurrentState(Campaign.State state, Pageable pageable);
}
