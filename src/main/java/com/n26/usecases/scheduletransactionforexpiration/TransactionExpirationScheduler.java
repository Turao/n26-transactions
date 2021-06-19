package com.n26.usecases.scheduletransactionforexpiration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.n26.domain.transaction.TransactionRepository;
import com.n26.usecases.updatestatistics.UpdateStatistics;
import com.n26.usecases.updatestatistics.UpdateStatisticsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TransactionExpirationScheduler implements ScheduleTransactionForExpiration {

  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExpirationScheduler.class);

  private final TransactionRepository transactionRepository;
  private final UpdateStatistics updateStatistics;

  public TransactionExpirationScheduler(
    final TransactionRepository transactionRepository,
    final UpdateStatistics updateStatistics
  ) {
    this.transactionRepository = transactionRepository;
    this.updateStatistics = updateStatistics;
  }

  @Async
  @Override
  public void execute(final ScheduleTransactionForExpirationRequest request) {
    UUID transactionId = request.getTransactionId();
    LOGGER.info("Scheduling expiration of Transaction: {}", transactionId);  

    try {
      TimeUnit.MILLISECONDS.sleep(request.getTimeToLiveMilliseconds());
    
      LOGGER.info("Transaction expired: {}. Removing...", transactionId);
      transactionRepository.removeOne(transactionId);

      afterExpiration();
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
    }
  }

  private void afterExpiration() {
    updateStatistics.execute(new UpdateStatisticsRequest());
  }
  
}
