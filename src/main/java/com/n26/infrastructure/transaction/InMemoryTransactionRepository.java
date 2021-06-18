package com.n26.infrastructure.transaction;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

import com.n26.domain.transaction.Transaction;
import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTransactionRepository.class);

  Collection<Transaction> transactions = new LinkedBlockingDeque<Transaction>();

  @Override
  public void insertOne(final Transaction transaction) {
    LOGGER.debug("Inserting transaction into in-memory database (mock)");
    transactions.add(transaction);
  }

  @Override
  public Collection<Transaction> getLastMinuteTransactions() {
    LOGGER.debug("Getting last minute transactions");
    transactions = discardTransactionsMoreThanAMinuteOld();
    return transactions;
  }
  
  private Collection<Transaction> discardTransactionsMoreThanAMinuteOld() {
    LOGGER.debug("Discarding all transactions more than a minute old...");
    return transactions
      .stream()
      .filter(this::isTransactionLessThanAMinuteOld)
      .collect(Collectors.toCollection(LinkedBlockingDeque::new));
  }

  private boolean isTransactionLessThanAMinuteOld(final Transaction transaction) {
    return transaction.getTimestamp().isAfter(OffsetDateTime.now().minusMinutes(1));
  }

  @Override
  public void removeAll() {
    LOGGER.debug("Removing all transactions");
    transactions.clear();    
  }
  
}
