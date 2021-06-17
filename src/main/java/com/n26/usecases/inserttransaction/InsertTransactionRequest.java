package com.n26.usecases.inserttransaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.lang.NonNull;

public class InsertTransactionRequest {
  private final BigDecimal amount;
  private final OffsetDateTime timestamp;

  public InsertTransactionRequest(
    final @NonNull BigDecimal amount,
    final @NonNull OffsetDateTime timestamp
  ) {
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }
}
