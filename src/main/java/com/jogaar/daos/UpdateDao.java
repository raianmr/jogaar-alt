package com.jogaar.daos;

import com.jogaar.entities.Update;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateDao extends JpaRepository<Update, Long> {
}
