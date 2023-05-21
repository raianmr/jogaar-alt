package com.jogaar.daos;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.Reward;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardDao extends JpaRepository<Reward, Long> {
    Page<Reward> findAllByCampaign(Campaign campaign, Pageable pageable);
}
