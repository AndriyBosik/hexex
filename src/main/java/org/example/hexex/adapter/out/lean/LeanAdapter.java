package org.example.hexex.adapter.out.lean;

import lombok.RequiredArgsConstructor;
import org.example.hexex.adapter.out.lean.client.LeanApiClient;
import org.example.hexex.adapter.out.lean.dto.LeanCustomerRequestDto;
import org.example.hexex.adapter.out.lean.dto.LeanCustomerResponseDto;
import org.example.hexex.adapter.out.lean.repository.LeanCustomerRepository;
import org.example.hexex.adapter.out.lean.repository.model.LeanCustomerEntity;
import org.example.hexex.application.port.out.GenerateLeanTokenPort;
import org.example.hexex.application.port.out.InitiatePaymentPort;
import org.example.hexex.application.port.out.LoadLeanCustomerPort;
import org.example.hexex.application.port.out.model.LeanTokenDto;
import org.example.hexex.common.annotation.Adapter;
import org.example.hexex.domain.model.payment.instant.InstantPayment;
import org.example.hexex.domain.model.payment.instant.InstantPaymentInfo;

import java.util.UUID;

@Adapter
@RequiredArgsConstructor
public class LeanAdapter implements
  InitiatePaymentPort<InstantPaymentInfo, InstantPayment>,
  LoadLeanCustomerPort,
  GenerateLeanTokenPort {
  private final LeanCustomerRepository customerRepository;
  private final LeanApiClient leanApiClient;

  @Override
  public InstantPayment transfer(InstantPaymentInfo request) {
    LeanCustomerEntity customer = ensureCustomerExists(request.email());

    return new InstantPayment(
      null,
      UUID.randomUUID().toString(),
      null,
      "PENDING_WITH_BANK",
      null,
      null,
      request.amount(),
      "AED");
  }

  @Override
  public LeanCustomerResponseDto transfer(String appUserId) {
    return leanApiClient.findCustomer(appUserId)
      .orElseGet(() -> leanApiClient.createCustomer(new LeanCustomerRequestDto(appUserId)));
  }

  @Override
  public LeanTokenDto exchange(String customerId) {
    return leanApiClient.getLeanCustomerToken(customerId);
  }

  private LeanCustomerEntity ensureCustomerExists(String email) {
    return customerRepository.findByEmailIgnoreCase(email)
      .orElseGet(() -> createCustomer(email));
  }

  private LeanCustomerEntity createCustomer(String email) {
    LeanCustomerResponseDto response = leanApiClient.createCustomer(new LeanCustomerRequestDto(email));
    LeanCustomerEntity entity = new LeanCustomerEntity();
    entity.setEmail(email);
    entity.setCustomerId(response.customerId());
    return customerRepository.save(entity);
  }
}
