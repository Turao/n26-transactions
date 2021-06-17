package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Transaction {
  private UUID transactionId;
  private BigDecimal amount;
  private OffsetDateTime timestamp;

  public Transaction(final BigDecimal amount, final OffsetDateTime timestamp) {
    this.transactionId = UUID.randomUUID();
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
