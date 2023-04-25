package com.jogaar.daos;

import com.jogaar.entities.Pledge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PledgeDao extends JpaRepository<Pledge, Long> {
}
