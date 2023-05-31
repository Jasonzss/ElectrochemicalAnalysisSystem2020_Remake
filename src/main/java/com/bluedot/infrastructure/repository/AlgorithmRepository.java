package com.bluedot.infrastructure.repository;

import com.bluedot.domain.algorithm.AbstractAlgorithm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 14:20
 * @Description ：
 */
public interface AlgorithmRepository extends JpaRepository<AbstractAlgorithm, String> {

}
