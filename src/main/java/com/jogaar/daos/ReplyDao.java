package com.jogaar.daos;

import com.jogaar.entities.Reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyDao extends JpaRepository<Reply, Long> {
}
