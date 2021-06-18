package com.n26.infrastructure.transaction;

import java.util.Collection;
import java.util.HashSet;

import com.n26.domain.transaction.Transaction;
import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTransactionRepository.class);

  final Collection<Transaction> transactions = new HashSet<Transaction>();

  @Override
  public void insertOne(Transaction transaction) {
    LOGGER.debug("inserting transaction into in-memory database (mock)");
    transactions.add(transaction);
  }

  @Override
  public Collection<Transaction> getLastMinuteTransactions() {
    LOGGER.debug("Getting last minute transactions");
    return transactions;
  }

  @Override
  public void removeAll() {
    LOGGER.debug("Removing all transactions");
    transactions.clear();    
  }
  
}
