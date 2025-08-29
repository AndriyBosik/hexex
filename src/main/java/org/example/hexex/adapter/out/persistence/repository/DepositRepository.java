package org.example.hexex.adapter.out.persistence.repository;

import org.example.hexex.adapter.out.persistence.repository.model.DepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DepositRepository extends JpaRepository<DepositEntity, Long>, JpaSpecificationExecutor<DepositEntity> {
}
