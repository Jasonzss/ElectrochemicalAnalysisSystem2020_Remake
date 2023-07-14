package com.bluedot.infrastructure.repository;

import com.bluedot.application.electrochemistry.dto.CurveData;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 21:24
 */
public interface CurveDataRepository extends JpaRepository<CurveData, Integer> {
}
