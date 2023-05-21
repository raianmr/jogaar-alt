package com.jogaar.daos;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends JpaRepository<Tag, Long> {
    Page<Tag> findAllByCampaign(Campaign campaign, Pageable pageable);
}
