package org.example.hexex.adapter.out.persistence.repository;

import org.example.hexex.adapter.out.persistence.repository.model.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
}
