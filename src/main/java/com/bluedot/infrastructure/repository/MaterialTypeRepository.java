package com.bluedot.infrastructure.repository;

import com.bluedot.infrastructure.repository.data_object.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jason
 * @creationDate 2023/07/11 - 16:19
 */
public interface MaterialTypeRepository extends JpaRepository<MaterialType, Integer> {
}
