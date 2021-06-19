package com.n26.usecases.scheduletransactionforexpiration;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.n26.usecases.expiretransaction.ExpireTransaction;
import com.n26.usecases.expiretransaction.ExpireTransactionRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransactionExpirationScheduler implements ScheduleTransactionForExpiration {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExpirationScheduler.class);
  private static final long EXPIRE_TRANSACTIONS_AFTER_SECONDS = TimeUnit.MINUTES.toSeconds(1);

  private final ScheduledExecutorService scheduler;
  private final ExpireTransaction expireTransaction;

  public TransactionExpirationScheduler(
    final ScheduledExecutorService scheduler,
    final ExpireTransaction expireTransaction
  ) {
    this.scheduler = scheduler;
    this.expireTransaction = expireTransaction;
  }

  @Override
  public void execute(final ScheduleTransactionForExpirationRequest request) {
    UUID transactionId = request.getTransactionId();
    LOGGER.info("Scheduling expiration of Transaction: {}", transactionId);  

    OffsetDateTime expirationDate = request.getTimestamp()
      .plusSeconds(EXPIRE_TRANSACTIONS_AFTER_SECONDS);
    
    long timeRemainingBeforeExpiration = OffsetDateTime.now()
      .until(expirationDate, ChronoUnit.MILLIS);

    LOGGER.info("Scheduling Transaction: {} to expire in {} Milliseconds",
      transactionId, timeRemainingBeforeExpiration);

    if (timeRemainingBeforeExpiration <= 0) {
      expireTransaction.execute(new ExpireTransactionRequest(transactionId));
      return;
    }

    scheduler.schedule(
      () -> expireTransaction.execute(new ExpireTransactionRequest(transactionId)),
      timeRemainingBeforeExpiration,
      TimeUnit.MILLISECONDS
    );
  }
  
}
