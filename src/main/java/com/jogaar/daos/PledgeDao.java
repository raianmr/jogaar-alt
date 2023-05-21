package com.jogaar.daos;

import com.jogaar.entities.Bookmark;
import com.jogaar.entities.Campaign;
import com.jogaar.entities.Pledge;
import com.jogaar.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PledgeDao extends JpaRepository<Pledge, Long> {
    Pledge findByCampaignAndPledger(Campaign campaign, User pledger);

    @Query("SELECT SUM(p.amount) FROM Pledge p WHERE p.campaign = :campaign")
    Long totalPledgedByCampaign(@Param("campaign") Campaign campaign);

    Page<Pledge> findAllByCampaign(Campaign campaign, Pageable pageable);
    Page<Pledge> findAllByPledger(User pledger, Pageable pageable);

    Long deleteByCampaignAndPledger(Campaign campaign, User user);
}
