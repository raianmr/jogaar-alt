package com.jogaar.daos;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.Milestone;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneDao extends JpaRepository<Milestone, Long> {
    Page<Milestone> findAllByCampaign(Campaign campaign, Pageable pageable);
}
