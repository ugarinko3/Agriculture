package org.example.repository;

import org.example.model.entity.District;
import org.example.model.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

/**
 * Работа с БД по фермеру
 */
public interface FarmerRepository  extends JpaRepository<Farmer, UUID>, JpaSpecificationExecutor<Farmer> {
}

