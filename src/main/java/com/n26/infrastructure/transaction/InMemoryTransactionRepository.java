package com.n26.infrastructure.transaction;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.n26.domain.transaction.Statistics;
import com.n26.domain.transaction.Transaction;
import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTransactionRepository
  implements TransactionRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTransactionRepository.class);

  final ConcurrentMap<UUID, Transaction> transactions = new ConcurrentHashMap<>();
  Statistics statistics = Statistics.from(new ArrayList<>());

  @Override
  public void insertOne(final Transaction transaction) {
    LOGGER.debug("Inserting transaction into in-memory database (mock)");
    transactions.put(transaction.getTransactionId(), transaction);
  }

  @Override
  public Collection<Transaction> getLastMinuteTransactions() {
    LOGGER.debug("Getting last minute transactions");
    return filterLessThanAMinuteOldTransactions(transactions);
  }
  
  private Collection<Transaction> filterLessThanAMinuteOldTransactions(ConcurrentMap<UUID, Transaction> transactions) {
    LOGGER.debug("Filtering all transactions less than a minute old");
    return transactions
      .values()
      .stream()
      .filter(this::isTransactionLessThanAMinuteOld)
      .collect(Collectors.toList());
  }

  private boolean isTransactionLessThanAMinuteOld(final Transaction transaction) {
    return !transaction.isOlderThan(OffsetDateTime.now().minusMinutes(1));
  }

  @Override
  public void removeAll() {
    LOGGER.debug("Removing all transactions");
    transactions.clear();    
  }

  @Override
  public void removeOne(UUID transactionId) {
    LOGGER.debug("Removing Transaction: {}", transactionId);
    transactions.remove(transactionId);
  }

  @Override
  public Statistics getStatistics() {
    return statistics;
  }

  @Override
  public void updateStatistics() {
    LOGGER.info("old statistics: {}", statistics);
    statistics = Statistics.from(getLastMinuteTransactions());
    LOGGER.info("new statistics: {}", statistics);
  }  
}
