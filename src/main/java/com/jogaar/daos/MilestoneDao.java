package com.jogaar.repository;

import com.jogaar.model.Milestone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
}
