package com.team.mystory.admin.report.content.repository;

import com.team.mystory.admin.report.content.entity.ContentReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentReportRepository extends JpaRepository<ContentReport, Long> , CustomContentReportRepository {

    long countBy();

}
