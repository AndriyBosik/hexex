package org.example.hexex.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.example.hexex.application.port.in.LeanTokenUseCase;
import org.example.hexex.application.port.out.model.LeanTokenDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lean")
public class LeanTokenResource {
  private final LeanTokenUseCase useCase;

  @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LeanTokenDto> generate() {
    LeanTokenDto dto = useCase.generate();
    return ResponseEntity.ok(dto);
  }
}
