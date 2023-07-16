package com.bluedot.infrastructure.repository;

import com.bluedot.application.electrochemistry.dto.CurveData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Jason
 * @creationDate 2023/07/13 - 21:24
 */
public interface CurveDataRepository extends JpaRepository<CurveData, Integer> {
    @Query(value = "select material_solubility, original_ip, newest_ip from curve_data where curve_data.curve_data_id in ?1", nativeQuery = true)
    List<CurveData> findCurveParameter(List<Integer> ids);
}
