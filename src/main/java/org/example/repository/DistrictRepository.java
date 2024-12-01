package org.example.repository;


import org.example.model.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import java.util.List;

/**
 * Работа с БД по району
 */
public interface DistrictRepository  extends JpaRepository<District, UUID>, JpaSpecificationExecutor<District> {
//    List<District> findByStatus(Boolean status);
//    List<District> findByNameAndCode(String name, Integer code);
//    List<District> findByNameContainingIgnoreCaseAndStatus(String name, Boolean status);
//    List<District> findByCodeAndStatus(Integer code, Boolean status);
}
