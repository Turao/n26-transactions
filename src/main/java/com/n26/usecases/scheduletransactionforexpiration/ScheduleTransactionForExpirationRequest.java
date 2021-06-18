package com.n26.usecases.scheduletransactionforexpiration;

import java.util.UUID;

public class ScheduleTransactionForExpirationRequest {
  private final UUID transactionId;
  private final long timeToLiveMilliseconds;

  public ScheduleTransactionForExpirationRequest(
    final UUID transactionId,
    final long timeToLiveMilliseconds
  ) {
    this.transactionId = transactionId;
    this.timeToLiveMilliseconds = timeToLiveMilliseconds;
  }

  public UUID getTransactionId() {
    return transactionId;
  }

  public long getTimeToLiveMilliseconds() {
    return timeToLiveMilliseconds;
  }
}
