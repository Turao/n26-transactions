package com.n26.usecases.updatestatistics;

import com.n26.domain.transaction.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StatisticsUpdater implements UpdateStatistics {

  private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsUpdater.class);

  private final TransactionRepository transactionRepository;

  public StatisticsUpdater(final TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

  @Async
  @Override
  public void execute(UpdateStatisticsRequest request) {
    LOGGER.info("Updating Statistics...");
    transactionRepository.updateStatistics();
  }

}
