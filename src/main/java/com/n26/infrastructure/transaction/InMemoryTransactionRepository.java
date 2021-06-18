package com.n26.infrastructure.transaction;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.n26.domain.transaction.Transaction;
import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTransactionRepository.class);

  final Collection<Transaction> transactions = new ArrayList<Transaction>();

  @Override
  public void insertOne(Transaction transaction) {
    LOGGER.debug("inserting transaction into in-memory database (mock)");
    transactions.add(transaction);
  }

  @Override
  public Collection<Transaction> getLastMinuteTransactions() {
    LOGGER.debug("Getting last minute transactions");
    return transactions
      .stream()
      .filter(this::isTransactionLessThanAMinuteOld)
      .collect(Collectors.toList());
  }

  private boolean isTransactionLessThanAMinuteOld(Transaction transaction) {
    return transaction.getTimestamp().isAfter(OffsetDateTime.now().minusMinutes(1));
  }

  @Override
  public void removeAll() {
    LOGGER.debug("Removing all transactions");
    transactions.clear();    
  }
  
}
