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
    @Query("SELECT COUNT(c) FROM Campaign c where c.currentState = 'GREENLIT'")
    Long countSuccessfulCampaigns();

    Page<Campaign> findAllByCurrentState(Campaign.State state, Pageable pageable);

    Page<Campaign> findAllByCurrentStateOrderByDeadlineDesc(Campaign.State state, Pageable pageable);

    Page<Campaign> findAllByCampaigner(User campaigner, Pageable pageable);

    @Query("SELECT c FROM Bookmark b INNER JOIN b.campaign c WHERE b.user.id = :userId ")
    Page<Campaign> findAllBookmarkedByUser(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT c FROM Pledge p INNER JOIN p.campaign c WHERE p.pledger.id = :userId ")
    Page<Campaign> findAllPledgedByUser(@Param("userId") Long userId, Pageable pageable);

    @Query(
            value = """
    select campaigns.*
    from campaigns left join tags
    on tags.campaign_id = campaigns.id
    where campaigns.current_state = 'STARTED' and campaigns.campaigner_id <> :forUserId
    group by campaigns.id
    order by count(
      tags.name in (
        select tags.name
        from tags inner join bookmarks
        on bookmarks.campaign_id = tags.campaign_id
        where bookmarks.user_id = :forUserId
        group by tags.name
      )
    ) desc;
    """,
            countQuery = "select count(*) from campaigns where campaigns.current_state = 'STARTED' and campaigns.campaigner_id <> :forUserId",
            nativeQuery = true
    )
    Page<Campaign> recommendedCampaigns(@Param("forUserId") Long forUserId, Pageable pageable);

    @Query("""
    SELECT DISTINCT c
    FROM Pledge p INNER JOIN p.campaign c
    WHERE c.currentState = 'STARTED'
    ORDER BY COUNT (DISTINCT p.pledger) DESC
""")
    Page<Campaign> featuredCampaigns(Pageable pageable);


    // remember to CREATE EXTENSION pg_trgm;
    @Query(
            value = """
            SELECT campaigns.* FROM campaigns
            ORDER BY WORD_SIMILARITY(campaigns.title, :searchTerm) DESC
    """,
            countQuery = "SELECT COUNT(*) FROM campaigns",
            nativeQuery = true
    )
    Page<Campaign> fuzzySearchCampaigns(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query(
            value = """
            update campaigns set current_state = 'ENDED'
            where current_state = 'STARTED' and deadline < now()
    """,
            nativeQuery = true
    )
    void updateAllEndedCampaigns();
}