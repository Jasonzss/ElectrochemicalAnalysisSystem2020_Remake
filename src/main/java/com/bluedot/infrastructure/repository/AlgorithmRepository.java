package com.bluedot.infrastructure.repository;

import com.bluedot.domain.algorithm.PersistantAlgorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Author Jason
 * @CreationDate 2023/05/30 - 14:20
 * @Description ：
 */
public interface AlgorithmRepository extends JpaRepository<PersistantAlgorithm, String> {
    @Query(value = "select * from algorithm where status = 1 or creator_email = ?1", nativeQuery = true)
    List<PersistantAlgorithm> listAvailable(String email);

    Optional<PersistantAlgorithm> findByAlgoIdAndCreatorEmail(String algoId, String email);
}
