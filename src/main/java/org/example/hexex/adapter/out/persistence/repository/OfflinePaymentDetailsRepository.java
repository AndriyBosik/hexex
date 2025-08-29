package org.example.hexex.adapter.out.persistence.repository;

import org.example.hexex.adapter.out.persistence.repository.model.OfflinePaymentDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflinePaymentDetailsRepository extends JpaRepository<OfflinePaymentDetailsEntity, Long> {
}
