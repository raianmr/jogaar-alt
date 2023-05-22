package com.jogaar.daos;

import com.jogaar.entities.Report;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDao extends JpaRepository<Report, Long> {
    Page<Report> findAllByContentType(Report.Reportable type, PageRequest of);
}
