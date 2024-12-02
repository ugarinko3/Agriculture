package org.example.repository;

import org.example.model.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

/**
 * Работа с БД по району
 */
public interface DistrictRepository  extends JpaRepository<District, UUID>, JpaSpecificationExecutor<District> {
}
