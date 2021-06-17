package com.n26.infrastructure.transaction;

import com.n26.domain.transaction.Transaction;
import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryTransactionRepository.class);

  @Override
  public void insertOne(Transaction transaction) {
    LOGGER.debug("inserting transaction into in-memory database (mock)");
  }
  
}
