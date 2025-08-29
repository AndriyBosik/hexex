package org.example.hexex.adapter.out.persistence.repository;

import org.example.hexex.adapter.out.persistence.repository.model.InstantPaymentDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstantPaymentDetailsRepository extends JpaRepository<InstantPaymentDetailsEntity, Long> {
}
