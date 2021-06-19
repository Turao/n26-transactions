package com.n26.usecases.removealltransactions;

import com.n26.core.Command;
import com.n26.domain.transaction.TransactionRepository;
import com.n26.usecases.updatestatistics.UpdateStatistics;
import com.n26.usecases.updatestatistics.UpdateStatisticsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RemoveAllTransactions implements Command<RemoveAllTransactionsRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoveAllTransactions.class);

  private final TransactionRepository transactionRepository;
  private final UpdateStatistics updateStatistics;

  public RemoveAllTransactions(
    final TransactionRepository transactionRepository,
    final UpdateStatistics updateStatistics
  ) {
    this.transactionRepository = transactionRepository;
    this.updateStatistics = updateStatistics;
  }

  @Override
  public void execute(RemoveAllTransactionsRequest request) {
    LOGGER.info("Removing all transactions...");
    transactionRepository.removeAll();

    afterRemoval();
  }

  private final void afterRemoval() {
    updateStatistics.execute(new UpdateStatisticsRequest());
  }
  
}
