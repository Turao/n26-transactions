package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Transaction {
  UUID transactionId;
  BigDecimal amount;
  OffsetDateTime timestamp;

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

  public boolean isOlderThan(OffsetDateTime dateTime) {
    return timestamp.isBefore(dateTime);
  }

}
