package com.bluedot.infrastructure.repository;

import com.bluedot.application.electrochemistry.dto.AnalyzedModelReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Jason
 * @creationDate 2023/07/16 - 21:40
 */
public interface AnalyzedModelReportRepository extends JpaRepository<AnalyzedModelReport, Integer> {
    Optional<AnalyzedModelReport> findByReportIdAndUserEmail(Integer integer, String email);
}
