package com.jogaar.daos;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignDao extends JpaRepository<Campaign, Long> {
    Page<Campaign> findAllByCurrentState(Campaign.State state, Pageable pageable);

    Page<Campaign> findAllByCurrentStateOrderByDeadlineDesc(Campaign.State state, Pageable pageable);

    Page<Campaign> findAllByCampaigner(User campaigner, Pageable pageable);

    @Query("SELECT c FROM Bookmark b INNER JOIN b.campaign c WHERE b.user.id = :userId ")
    Page<Campaign> findAllBookmarkedByUser(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT c FROM Pledge p INNER JOIN p.campaign c WHERE p.pledger.id = :userId ")
    Page<Campaign> findAllPledgedByUser(@Param("userId") Long userId, Pageable pageable);

    //    TODO: Fix this query
    @Query("""
        SELECT c FROM Tag t FULL OUTER JOIN t.campaign c
        WHERE c.currentState = 'STARTED' AND c.campaigner.id <> :forUserId
        GROUP BY c.id ORDER BY (
            SELECT DESC (COUNT(t2.id)) FROM Tag t2 WHERE t2.campaign.id = c.id
        )
    """)
    Page<Campaign> recommendedCampaigns(@Param("forUserId") Long forUserId, Pageable pageable);
}
