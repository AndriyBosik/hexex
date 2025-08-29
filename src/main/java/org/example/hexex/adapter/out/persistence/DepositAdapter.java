package org.example.hexex.adapter.out.persistence;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.example.hexex.adapter.out.persistence.repository.DepositRepository;
import org.example.hexex.adapter.out.persistence.repository.InstantPaymentDetailsRepository;
import org.example.hexex.adapter.out.persistence.repository.model.DepositEntity;
import org.example.hexex.adapter.out.persistence.repository.model.InstantPaymentDetailsEntity;
import org.example.hexex.adapter.out.persistence.repository.model.OfflinePaymentDetailsEntity;
import org.example.hexex.adapter.out.persistence.repository.model.WalletEntity;
import org.example.hexex.application.port.out.StoreDepositPort;
import org.example.hexex.application.port.out.model.LoadDepositPort;
import org.example.hexex.application.port.out.result.StoreDepositResultPort;
import org.example.hexex.common.annotation.Adapter;
import org.example.hexex.domain.exception.HexexBusinessRuntimeException;
import org.example.hexex.domain.model.Deposit;
import org.example.hexex.domain.model.payment.instant.InstantPayment;
import org.example.hexex.domain.model.payment.instant.OfflinePayment;
import org.example.hexex.domain.model.payment.instant.Payment;
import org.example.hexex.domain.model.result.DepositResult;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class DepositAdapter implements
  StoreDepositPort,
  StoreDepositResultPort,
  LoadDepositPort {
  private final DepositRepository depositRepository;
  private final InstantPaymentDetailsRepository instantPaymentDetailsRepository;

  @Override
  public Deposit transfer(Deposit deposit) {
    DepositEntity entity = depositRepository.findById(deposit.getId())
      .orElseThrow();
    entity.setTransactionStatus(deposit.getStatus());
    DepositEntity saved = depositRepository.save(entity);
    return new Deposit(
      saved.getId(),
      saved.getTransactionStatus(),
      saved.getWallet().getId(),
      saved.getCreatedAt(),
      saved.getUpdatedAt());
  }

  @Override
  public DepositResult transfer(DepositResult result) {
    WalletEntity wallet = new WalletEntity();
    wallet.setId(result.deposit().getWalletId());

    DepositEntity entity = new DepositEntity();
    entity.setAmount(result.payment().amount());
    entity.setWallet(wallet);
    entity.setTransactionStatus(result.deposit().getStatus());
    DepositEntity saved = depositRepository.save(entity);
    Payment payment = storePaymentDetails(entity, result.payment());
    instantPaymentDetailsRepository.save(entity.getInstantDetails());
    return new DepositResult(
      new Deposit(
        saved.getId(),
        saved.getTransactionStatus(),
        saved.getWallet().getId(),
        saved.getCreatedAt(),
        saved.getUpdatedAt()),
      payment);
  }

  @Override
  public Optional<Deposit> transfer(Payment payment) {
    return depositRepository.findOne(specification(payment))
      .map(entity -> new Deposit(
        entity.getId(),
        entity.getTransactionStatus(),
        entity.getWallet().getId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt()));
  }

  private Payment storePaymentDetails(DepositEntity deposit, Payment payment) {
    return switch (payment) {
      case InstantPayment instantPayment -> storeInstantPaymentDetails(deposit, instantPayment);
      case OfflinePayment offlinePayment -> storeOfflinePaymentDetails(deposit, offlinePayment);
    };
  }

  private Specification<DepositEntity> specification(Payment payment) {
    return (root, query, criteriaBuilder) -> {
      if (query == null) {
        throw new HexexBusinessRuntimeException("Unable to compose query");
      }
      switch (payment) {
        case InstantPayment instant -> {
          Subquery<Integer> subQuery = query.subquery(Integer.class);
          Root<InstantPaymentDetailsEntity> subRoot = subQuery.from(InstantPaymentDetailsEntity.class);

          subQuery.select(criteriaBuilder.literal(1))
            .where(
              criteriaBuilder.equal(subRoot.get("id"), instant.id()),
              criteriaBuilder.equal(subRoot.get("depositId"), root.get("id")));

          return criteriaBuilder.exists(subQuery);
        }
        case OfflinePayment offline -> {
          Subquery<Integer> subQuery = query.subquery(Integer.class);
          Root<OfflinePaymentDetailsEntity> subRoot = subQuery.from(OfflinePaymentDetailsEntity.class);

          subQuery.select(criteriaBuilder.literal(1))
            .where(
              criteriaBuilder.equal(subRoot.get("id"), offline.id()),
              criteriaBuilder.equal(subRoot.get("depositId"), root.get("id")));

          return criteriaBuilder.exists(subQuery);
        }
      }
    };
  }

  private Payment storeInstantPaymentDetails(DepositEntity deposit, InstantPayment payment) {
    InstantPaymentDetailsEntity entity = new InstantPaymentDetailsEntity();
    entity.setAmount(payment.amount());
    entity.setPaymentId(payment.paymentId());
    entity.setIntentId(payment.intentId());
    entity.setStatus(payment.status());
    entity.setCurrency(payment.currency());
    entity.setDepositId(deposit.getId());
    deposit.setInstantDetails(entity);

    return new InstantPayment(
      entity.getId(),
      entity.getIntentId(),
      entity.getPaymentId(),
      entity.getStatus(),
      null,
      null,
      entity.getAmount(),
      entity.getCurrency());
  }

  private Payment storeOfflinePaymentDetails(DepositEntity deposit, OfflinePayment payment) {
    OfflinePaymentDetailsEntity entity = new OfflinePaymentDetailsEntity();
    entity.setAmount(payment.amount());
    entity.setStatus(payment.transactionStatus());
    entity.setCurrency(payment.currency());
    entity.setBankName(payment.bankName());
    entity.setBankAccountNumber(payment.bankAccountNumber());
    entity.setDepositId(deposit.getId());
    deposit.setOfflineDetails(entity);
    return new OfflinePayment(
      entity.getId(),
      entity.getAmount(),
      entity.getCurrency(),
      entity.getStatus(),
      entity.getBankName(),
      entity.getBankAccountNumber());
  }
}
