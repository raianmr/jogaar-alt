package com.jogaar.repository;

import com.jogaar.model.Faq;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
