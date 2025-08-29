package org.example.hexex.adapter.out.persistence.repository;

import org.example.hexex.adapter.out.persistence.repository.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
  @Query("select w from WalletEntity w where w.user.email = :email and w.currency.code = :currencyCode")
  Optional<WalletEntity> findByUserEmailAndCurrencyCode(
    @Param("email") String email,
    @Param("currencyCode") String currencyCode);
}
