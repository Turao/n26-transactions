package com.n26.usecases.expiretransaction;

import java.util.UUID;

public class ExpireTransactionRequest {
  private final UUID transactionId;

  public ExpireTransactionRequest(final UUID transactionId) {
    this.transactionId = transactionId;
  }
  
  public UUID getTransactionId() {
    return transactionId;
  }
}
