package com.n26.usecases.inserttransaction;

import java.time.Duration;
import java.time.OffsetDateTime;

import com.n26.core.Command;
import com.n26.domain.transaction.Transaction;
import com.n26.domain.transaction.TransactionRepository;
import com.n26.usecases.scheduletransactionforexpiration.ScheduleTransactionForExpiration;
import com.n26.usecases.scheduletransactionforexpiration.ScheduleTransactionForExpirationRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InsertTransaction implements Command<InsertTransactionRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(InsertTransaction.class);

  private final TransactionRepository transactionRepository;
  private final ScheduleTransactionForExpiration scheduleTransactionForExpiration;

  public InsertTransaction(
    final TransactionRepository transactionRepository,
    final ScheduleTransactionForExpiration scheduleTransactionForExpiration) {
    this.transactionRepository = transactionRepository;
    this.scheduleTransactionForExpiration = scheduleTransactionForExpiration;
  }

  @Override
  public void execute(final InsertTransactionRequest request) {
    LOGGER.debug("Processing InsertTransactionRequest: {}", request);

    Transaction transaction = new Transaction(
      request.getAmount(),
      request.getTimestamp()
    );

    if (transaction.isOlderThan(OffsetDateTime.now().minusMinutes(1))) {
      LOGGER.info("Transaction is older than 1 minute");
      throw new MoreThanAMinuteOldException();
    }

    LOGGER.debug("Inserting Transaction...");
    transactionRepository.insertOne(transaction);
    LOGGER.debug("Transaction inserted");

    // side-effects (can be replaced for events at a later point in time)
    afterInsertion(transaction);
  }
  
  private void afterInsertion(Transaction transaction) {
    Duration timeRemainingBeforeExpiration = Duration.between(
      OffsetDateTime.now(),
      transaction.getTimestamp().plusMinutes(1)
    );

    scheduleTransactionForExpiration.execute(
      new ScheduleTransactionForExpirationRequest(
        transaction.getTransactionId(),
        timeRemainingBeforeExpiration.toMillis()
      )
    );
  }
}
