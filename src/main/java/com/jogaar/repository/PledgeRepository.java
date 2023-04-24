package com.jogaar.repository;

import com.jogaar.model.Pledge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PledgeRepository extends JpaRepository<Pledge, Long> {
}
