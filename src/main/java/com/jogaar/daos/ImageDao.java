package com.jogaar.daos;

import com.jogaar.entities.Image;
import com.jogaar.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageDao extends JpaRepository<Image, Long> {
    Page<Image> findAllByUploader(User uploader, Pageable pageable);
}
