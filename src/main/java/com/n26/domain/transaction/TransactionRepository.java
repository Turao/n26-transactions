package com.n26.domain.transaction;

import java.util.Collection;

public interface TransactionRepository {
  void insertOne(Transaction transaction);
  Collection<Transaction> getLastMinuteTransactions();
  void removeAll();
  void removeLastMinuteTransactions();
}
