package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Value;

@Value
public class Transaction {
  private UUID transactionId;
  private BigDecimal amount;
  private OffsetDateTime timestamp;
}
