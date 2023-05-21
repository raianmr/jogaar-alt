package com.jogaar.daos;

import com.jogaar.entities.Vote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteDao extends JpaRepository<Vote, Long> {
    @Query("SELECT v FROM Vote v INNER JOIN v.reply r WHERE r.user.id = :userId ORDER BY v.createdAt DESC")
    Page<Vote> voteAlerts(@Param("userId") Long userId, Pageable pageable);
}
