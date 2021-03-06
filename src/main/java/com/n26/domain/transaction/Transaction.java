package com.n26.domain.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Transaction {
  UUID transactionId;
  Amount amount;
  OffsetDateTime timestamp;

  public Transaction(final BigDecimal amount, final OffsetDateTime timestamp) {
    this.transactionId = UUID.randomUUID();    
    this.amount = new Amount(amount);

    if (timestamp.isAfter(OffsetDateTime.now())) {
      throw new IllegalArgumentException("Transaction date is in the future");
    }
    
    this.timestamp = timestamp;
  }


  public UUID getTransactionId() {
    return transactionId;
  }

  public Amount getAmount() {
    return amount;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public boolean isOlderThan(OffsetDateTime dateTime) {
    return timestamp.isBefore(dateTime);
  }
}
