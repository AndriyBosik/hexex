package org.example.hexex.adapter.out.lean.repository;

import org.example.hexex.adapter.out.lean.repository.model.LeanCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LeanCustomerRepository extends JpaRepository<LeanCustomerEntity, String> {
  Optional<LeanCustomerEntity> findByEmailIgnoreCase(
    @Param("email") String email);
}
