package com.jogaar.daos;

import com.jogaar.entities.Vote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteDao extends JpaRepository<Vote, Long> {
}
