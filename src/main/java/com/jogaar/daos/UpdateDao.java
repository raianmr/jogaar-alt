package com.jogaar.repository;

import com.jogaar.model.Update;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateRepository extends JpaRepository<Update, Long> {
}
