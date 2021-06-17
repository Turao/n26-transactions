package com.n26.usecases.inserttransaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.lang.NonNull;

public class InsertTransactionRequest {
  private final UUID transactionId;
  private final BigDecimal amount;
  private final OffsetDateTime timestamp;

  public InsertTransactionRequest(
    final @NonNull UUID transactionId, 
    final @NonNull BigDecimal amount,
    final @NonNull OffsetDateTime timestamp
  ) {
    this.transactionId = transactionId;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }
}
