package com.jogaar.daos;

import com.jogaar.entities.Reply;
import com.jogaar.entities.Vote;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyDao extends JpaRepository<Reply, Long> {
    @Query("SELECT COUNT(*) FROM Vote v INNER JOIN v.reply r WHERE r.id = :replyId")
    Long scoreForReply(@Param("replyId") Long replyId);

    @Query("SELECT r FROM Reply r INNER JOIN r.update u WHERE u.user.id = :userId ORDER BY r.createdAt DESC")
    Page<Vote> replyAlerts(@Param("userId") Long userId, Pageable pageable);
}
