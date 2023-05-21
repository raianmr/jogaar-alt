package com.jogaar.daos;

import com.jogaar.entities.Bookmark;
import com.jogaar.entities.Campaign;
import com.jogaar.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkDao extends JpaRepository<Bookmark, Long> {
    Page<Bookmark> findAllByCampaign(Campaign campaign, Pageable pageable);
    Page<Bookmark> findAllByUser(User user, Pageable pageable);
    Bookmark findByCampaignAndUser(Campaign campaign, User user);
    Long deleteByCampaignAndUser(Campaign campaign, User user);
}
