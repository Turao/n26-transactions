package com.n26.domain.transaction;

import java.util.Collection;
import java.util.UUID;

public interface TransactionRepository {
  void insertOne(Transaction transaction);
  Collection<Transaction> getLastMinuteTransactions();
  void removeAll();
  void removeOne(UUID transactionId);

  Statistics getStatistics();
  void updateStatistics();
}
