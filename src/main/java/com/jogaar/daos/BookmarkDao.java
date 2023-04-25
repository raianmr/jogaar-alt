package com.jogaar.daos;

import com.jogaar.entities.Bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkDao extends JpaRepository<Bookmark, Long> {
}
