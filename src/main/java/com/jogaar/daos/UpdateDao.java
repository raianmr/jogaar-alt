package com.jogaar.daos;

import com.jogaar.entities.Campaign;
import com.jogaar.entities.Reward;
import com.jogaar.entities.Update;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateDao extends JpaRepository<Update, Long> {
    Page<Update> findAllByCampaign(Campaign campaign, Pageable pageable);
}
