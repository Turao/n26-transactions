package com.n26.usecases.scheduletransactionforexpiration;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ScheduleTransactionForExpirationRequest {
  private final UUID transactionId;
  private final OffsetDateTime timestamp;

  public ScheduleTransactionForExpirationRequest(
    final UUID transactionId,
    final OffsetDateTime timestamp
  ) {
    this.transactionId = transactionId;
    this.timestamp = timestamp;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public OffsetDateTime getTimestamp() {
    return timestamp;
  }
}
