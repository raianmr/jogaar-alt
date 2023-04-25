package com.jogaar.daos;

import com.jogaar.entities.Reward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardDao extends JpaRepository<Reward, Long> {
}
