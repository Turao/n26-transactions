package com.n26.integration.web.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsertTransactionRequestDTO {
  private BigDecimal amount;
  private OffsetDateTime timestamp; 
}
